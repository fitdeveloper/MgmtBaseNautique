import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDealership } from 'app/shared/model/dealership.model';

type EntityResponseType = HttpResponse<IDealership>;
type EntityArrayResponseType = HttpResponse<IDealership[]>;

@Injectable({ providedIn: 'root' })
export class DealershipService {
  public resourceUrl = SERVER_API_URL + 'api/dealerships';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/dealerships';

  constructor(protected http: HttpClient) {}

  create(dealership: IDealership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dealership);
    return this.http
      .post<IDealership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dealership: IDealership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dealership);
    return this.http
      .put<IDealership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDealership>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDealership[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDealership[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(dealership: IDealership): IDealership {
    const copy: IDealership = Object.assign({}, dealership, {
      endDateDealership:
        dealership.endDateDealership && dealership.endDateDealership.isValid()
          ? dealership.endDateDealership.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.endDateDealership = res.body.endDateDealership ? moment(res.body.endDateDealership) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dealership: IDealership) => {
        dealership.endDateDealership = dealership.endDateDealership ? moment(dealership.endDateDealership) : undefined;
      });
    }
    return res;
  }
}
