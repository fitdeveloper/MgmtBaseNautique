import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IInsurance } from 'app/shared/model/insurance.model';

type EntityResponseType = HttpResponse<IInsurance>;
type EntityArrayResponseType = HttpResponse<IInsurance[]>;

@Injectable({ providedIn: 'root' })
export class InsuranceService {
  public resourceUrl = SERVER_API_URL + 'api/insurances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/insurances';

  constructor(protected http: HttpClient) {}

  create(insurance: IInsurance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(insurance);
    return this.http
      .post<IInsurance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(insurance: IInsurance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(insurance);
    return this.http
      .put<IInsurance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInsurance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInsurance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInsurance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(insurance: IInsurance): IInsurance {
    const copy: IInsurance = Object.assign({}, insurance, {
      endDateInsurance:
        insurance.endDateInsurance && insurance.endDateInsurance.isValid() ? insurance.endDateInsurance.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.endDateInsurance = res.body.endDateInsurance ? moment(res.body.endDateInsurance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((insurance: IInsurance) => {
        insurance.endDateInsurance = insurance.endDateInsurance ? moment(insurance.endDateInsurance) : undefined;
      });
    }
    return res;
  }
}
