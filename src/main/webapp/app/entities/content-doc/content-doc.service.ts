import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IContentDoc } from 'app/shared/model/content-doc.model';

type EntityResponseType = HttpResponse<IContentDoc>;
type EntityArrayResponseType = HttpResponse<IContentDoc[]>;

@Injectable({ providedIn: 'root' })
export class ContentDocService {
  public resourceUrl = SERVER_API_URL + 'api/content-docs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/content-docs';

  constructor(protected http: HttpClient) {}

  create(contentDoc: IContentDoc): Observable<EntityResponseType> {
    return this.http.post<IContentDoc>(this.resourceUrl, contentDoc, { observe: 'response' });
  }

  update(contentDoc: IContentDoc): Observable<EntityResponseType> {
    return this.http.put<IContentDoc>(this.resourceUrl, contentDoc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContentDoc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContentDoc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContentDoc[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
