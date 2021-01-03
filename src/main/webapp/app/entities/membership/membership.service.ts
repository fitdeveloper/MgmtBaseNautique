import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMembership } from 'app/shared/model/membership.model';

type EntityResponseType = HttpResponse<IMembership>;
type EntityArrayResponseType = HttpResponse<IMembership[]>;

@Injectable({ providedIn: 'root' })
export class MembershipService {
  public resourceUrl = SERVER_API_URL + 'api/memberships';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/memberships';

  constructor(protected http: HttpClient) {}

  create(membership: IMembership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membership);
    return this.http
      .post<IMembership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(membership: IMembership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membership);
    return this.http
      .put<IMembership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMembership>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMembership[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMembership[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(membership: IMembership): IMembership {
    const copy: IMembership = Object.assign({}, membership, {
      startDateMembership:
        membership.startDateMembership && membership.startDateMembership.isValid()
          ? membership.startDateMembership.format(DATE_FORMAT)
          : undefined,
      endDateMembership:
        membership.endDateMembership && membership.endDateMembership.isValid()
          ? membership.endDateMembership.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDateMembership = res.body.startDateMembership ? moment(res.body.startDateMembership) : undefined;
      res.body.endDateMembership = res.body.endDateMembership ? moment(res.body.endDateMembership) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((membership: IMembership) => {
        membership.startDateMembership = membership.startDateMembership ? moment(membership.startDateMembership) : undefined;
        membership.endDateMembership = membership.endDateMembership ? moment(membership.endDateMembership) : undefined;
      });
    }
    return res;
  }
}
