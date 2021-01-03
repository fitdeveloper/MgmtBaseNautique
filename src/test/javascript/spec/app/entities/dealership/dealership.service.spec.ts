import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DealershipService } from 'app/entities/dealership/dealership.service';
import { IDealership, Dealership } from 'app/shared/model/dealership.model';

describe('Service Tests', () => {
  describe('Dealership Service', () => {
    let injector: TestBed;
    let service: DealershipService;
    let httpMock: HttpTestingController;
    let elemDefault: IDealership;
    let expectedResult: IDealership | IDealership[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DealershipService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Dealership(0, 'AAAAAAA', currentDate, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            endDateDealership: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Dealership', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            endDateDealership: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateDealership: currentDate,
          },
          returnedFromService
        );

        service.create(new Dealership()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Dealership', () => {
        const returnedFromService = Object.assign(
          {
            numberDealership: 'BBBBBB',
            endDateDealership: currentDate.format(DATE_FORMAT),
            validDealership: true,
            descDealership: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateDealership: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Dealership', () => {
        const returnedFromService = Object.assign(
          {
            numberDealership: 'BBBBBB',
            endDateDealership: currentDate.format(DATE_FORMAT),
            validDealership: true,
            descDealership: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDateDealership: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Dealership', () => {
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
