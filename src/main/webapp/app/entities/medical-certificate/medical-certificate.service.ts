import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMedicalCertificate } from 'app/shared/model/medical-certificate.model';

type EntityResponseType = HttpResponse<IMedicalCertificate>;
type EntityArrayResponseType = HttpResponse<IMedicalCertificate[]>;

@Injectable({ providedIn: 'root' })
export class MedicalCertificateService {
  public resourceUrl = SERVER_API_URL + 'api/medical-certificates';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/medical-certificates';

  constructor(protected http: HttpClient) {}

  create(medicalCertificate: IMedicalCertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificate);
    return this.http
      .post<IMedicalCertificate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalCertificate: IMedicalCertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificate);
    return this.http
      .put<IMedicalCertificate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalCertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalCertificate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalCertificate[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(medicalCertificate: IMedicalCertificate): IMedicalCertificate {
    const copy: IMedicalCertificate = Object.assign({}, medicalCertificate, {
      endDateMedicalCertificate:
        medicalCertificate.endDateMedicalCertificate && medicalCertificate.endDateMedicalCertificate.isValid()
          ? medicalCertificate.endDateMedicalCertificate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.endDateMedicalCertificate = res.body.endDateMedicalCertificate ? moment(res.body.endDateMedicalCertificate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicalCertificate: IMedicalCertificate) => {
        medicalCertificate.endDateMedicalCertificate = medicalCertificate.endDateMedicalCertificate
          ? moment(medicalCertificate.endDateMedicalCertificate)
          : undefined;
      });
    }
    return res;
  }
}
