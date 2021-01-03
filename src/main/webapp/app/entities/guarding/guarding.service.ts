import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IGuarding } from 'app/shared/model/guarding.model';

type EntityResponseType = HttpResponse<IGuarding>;
type EntityArrayResponseType = HttpResponse<IGuarding[]>;

@Injectable({ providedIn: 'root' })
export class GuardingService {
  public resourceUrl = SERVER_API_URL + 'api/guardings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/guardings';

  constructor(protected http: HttpClient) {}

  create(guarding: IGuarding): Observable<EntityResponseType> {
    return this.http.post<IGuarding>(this.resourceUrl, guarding, { observe: 'response' });
  }

  update(guarding: IGuarding): Observable<EntityResponseType> {
    return this.http.put<IGuarding>(this.resourceUrl, guarding, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGuarding>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuarding[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuarding[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
