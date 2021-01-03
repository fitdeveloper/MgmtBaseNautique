import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMember } from 'app/shared/model/member.model';

type EntityResponseType = HttpResponse<IMember>;
type EntityArrayResponseType = HttpResponse<IMember[]>;

@Injectable({ providedIn: 'root' })
export class MemberService {
  public resourceUrl = SERVER_API_URL + 'api/members';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/members';

  constructor(protected http: HttpClient) {}

  create(member: IMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(member);
    return this.http
      .post<IMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(member: IMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(member);
    return this.http
      .put<IMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMember>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMember[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMember[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(member: IMember): IMember {
    const copy: IMember = Object.assign({}, member, {
      dobMember: member.dobMember && member.dobMember.isValid() ? member.dobMember.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dobMember = res.body.dobMember ? moment(res.body.dobMember) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((member: IMember) => {
        member.dobMember = member.dobMember ? moment(member.dobMember) : undefined;
      });
    }
    return res;
  }
}
