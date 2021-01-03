import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InsuranceService } from 'app/entities/insurance/insurance.service';
import { IInsurance, Insurance } from 'app/shared/model/insurance.model';

describe('Service Tests', () => {
  describe('Insurance Service', () => {
    let injector: TestBed;
    let service: InsuranceService;
    let httpMock: HttpTestingController;
    let elemDefault: IInsurance;
    let expectedResult: IInsurance | IInsurance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InsuranceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Insurance(0, 'AAAAAAA', currentDate, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            endDateInsurance: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Insurance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            endDateInsurance: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateInsurance: currentDate,
          },
          returnedFromService
        );

        service.create(new Insurance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Insurance', () => {
        const returnedFromService = Object.assign(
          {
            numberInsurance: 'BBBBBB',
            endDateInsurance: currentDate.format(DATE_FORMAT),
            validInsurance: true,
            descInsurance: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateInsurance: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Insurance', () => {
        const returnedFromService = Object.assign(
          {
            numberInsurance: 'BBBBBB',
            endDateInsurance: currentDate.format(DATE_FORMAT),
            validInsurance: true,
            descInsurance: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateInsurance: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Insurance', () => {
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
