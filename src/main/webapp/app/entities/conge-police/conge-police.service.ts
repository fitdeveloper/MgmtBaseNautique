import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICongePolice } from 'app/shared/model/conge-police.model';

type EntityResponseType = HttpResponse<ICongePolice>;
type EntityArrayResponseType = HttpResponse<ICongePolice[]>;

@Injectable({ providedIn: 'root' })
export class CongePoliceService {
  public resourceUrl = SERVER_API_URL + 'api/conge-polices';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/conge-polices';

  constructor(protected http: HttpClient) {}

  create(congePolice: ICongePolice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(congePolice);
    return this.http
      .post<ICongePolice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(congePolice: ICongePolice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(congePolice);
    return this.http
      .put<ICongePolice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICongePolice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICongePolice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICongePolice[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(congePolice: ICongePolice): ICongePolice {
    const copy: ICongePolice = Object.assign({}, congePolice, {
      endDateCongePolice:
        congePolice.endDateCongePolice && congePolice.endDateCongePolice.isValid()
          ? congePolice.endDateCongePolice.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.endDateCongePolice = res.body.endDateCongePolice ? moment(res.body.endDateCongePolice) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((congePolice: ICongePolice) => {
        congePolice.endDateCongePolice = congePolice.endDateCongePolice ? moment(congePolice.endDateCongePolice) : undefined;
      });
    }
    return res;
  }
}
