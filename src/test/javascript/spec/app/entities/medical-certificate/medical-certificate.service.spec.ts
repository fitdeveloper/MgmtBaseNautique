import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MedicalCertificateService } from 'app/entities/medical-certificate/medical-certificate.service';
import { IMedicalCertificate, MedicalCertificate } from 'app/shared/model/medical-certificate.model';

describe('Service Tests', () => {
  describe('MedicalCertificate Service', () => {
    let injector: TestBed;
    let service: MedicalCertificateService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicalCertificate;
    let expectedResult: IMedicalCertificate | IMedicalCertificate[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MedicalCertificateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MedicalCertificate(0, 'AAAAAAA', currentDate, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            endDateMedicalCertificate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MedicalCertificate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            endDateMedicalCertificate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateMedicalCertificate: currentDate,
          },
          returnedFromService
        );

        service.create(new MedicalCertificate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MedicalCertificate', () => {
        const returnedFromService = Object.assign(
          {
            numberMedicalCertificate: 'BBBBBB',
            endDateMedicalCertificate: currentDate.format(DATE_FORMAT),
            validMedicalCertificate: true,
            descMedicalCertificate: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateMedicalCertificate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MedicalCertificate', () => {
        const returnedFromService = Object.assign(
          {
            numberMedicalCertificate: 'BBBBBB',
            endDateMedicalCertificate: currentDate.format(DATE_FORMAT),
            validMedicalCertificate: true,
            descMedicalCertificate: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateMedicalCertificate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MedicalCertificate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
