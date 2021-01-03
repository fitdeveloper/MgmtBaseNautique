import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MemberService } from 'app/entities/member/member.service';
import { IMember, Member } from 'app/shared/model/member.model';
import { MemberType } from 'app/shared/model/enumerations/member-type.model';

describe('Service Tests', () => {
  describe('Member Service', () => {
    let injector: TestBed;
    let service: MemberService;
    let httpMock: HttpTestingController;
    let elemDefault: IMember;
    let expectedResult: IMember | IMember[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MemberService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Member(
        0,
        'AAAAAAA',
        MemberType.MEMBER_INDIVIDUAL,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dobMember: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Member', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dobMember: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dobMember: currentDate,
          },
          returnedFromService
        );

        service.create(new Member()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Member', () => {
        const returnedFromService = Object.assign(
          {
            numberMember: 'BBBBBB',
            typeMember: 'BBBBBB',
            cinMember: 'BBBBBB',
            firstnameMember: 'BBBBBB',
            lastnameMember: 'BBBBBB',
            emailMember: 'BBBBBB',
            numberPhoneMember: 'BBBBBB',
            dobMember: currentDate.format(DATE_FORMAT),
            adressMember: 'BBBBBB',
            imageMember: 'BBBBBB',
            descMember: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dobMember: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Member', () => {
        const returnedFromService = Object.assign(
          {
            numberMember: 'BBBBBB',
            typeMember: 'BBBBBB',
            cinMember: 'BBBBBB',
            firstnameMember: 'BBBBBB',
            lastnameMember: 'BBBBBB',
            emailMember: 'BBBBBB',
            numberPhoneMember: 'BBBBBB',
            dobMember: currentDate.format(DATE_FORMAT),
            adressMember: 'BBBBBB',
            imageMember: 'BBBBBB',
            descMember: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dobMember: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Member', () => {
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
