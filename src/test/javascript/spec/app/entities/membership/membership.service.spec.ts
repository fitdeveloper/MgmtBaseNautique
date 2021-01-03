import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MembershipService } from 'app/entities/membership/membership.service';
import { IMembership, Membership } from 'app/shared/model/membership.model';

describe('Service Tests', () => {
  describe('Membership Service', () => {
    let injector: TestBed;
    let service: MembershipService;
    let httpMock: HttpTestingController;
    let elemDefault: IMembership;
    let expectedResult: IMembership | IMembership[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MembershipService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Membership(0, 'AAAAAAA', 0, currentDate, currentDate, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startDateMembership: currentDate.format(DATE_FORMAT),
            endDateMembership: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Membership', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDateMembership: currentDate.format(DATE_FORMAT),
            endDateMembership: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDateMembership: currentDate,
            endDateMembership: currentDate,
          },
          returnedFromService
        );

        service.create(new Membership()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Membership', () => {
        const returnedFromService = Object.assign(
          {
            numberMembership: 'BBBBBB',
            amountMembership: 1,
            startDateMembership: currentDate.format(DATE_FORMAT),
            endDateMembership: currentDate.format(DATE_FORMAT),
            validMembership: true,
            descMembership: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDateMembership: currentDate,
            endDateMembership: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Membership', () => {
        const returnedFromService = Object.assign(
          {
            numberMembership: 'BBBBBB',
            amountMembership: 1,
            startDateMembership: currentDate.format(DATE_FORMAT),
            endDateMembership: currentDate.format(DATE_FORMAT),
            validMembership: true,
            descMembership: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDateMembership: currentDate,
            endDateMembership: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Membership', () => {
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
