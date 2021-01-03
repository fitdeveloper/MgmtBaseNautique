import { Moment } from 'moment';
import { IMembership } from 'app/shared/model/membership.model';
import { MemberType } from 'app/shared/model/enumerations/member-type.model';

export interface IMember {
  id?: number;
  numberMember?: string;
  typeMember?: MemberType;
  cinMember?: string;
  firstnameMember?: string;
  lastnameMember?: string;
  emailMember?: string;
  numberPhoneMember?: string;
  dobMember?: Moment;
  adressMember?: string;
  imageMemberContentType?: string;
  imageMember?: any;
  descMember?: string;
  parentId?: number;
  memberships?: IMembership[];
  childId?: number;
  associationId?: number;
}

export class Member implements IMember {
  constructor(
    public id?: number,
    public numberMember?: string,
    public typeMember?: MemberType,
    public cinMember?: string,
    public firstnameMember?: string,
    public lastnameMember?: string,
    public emailMember?: string,
    public numberPhoneMember?: string,
    public dobMember?: Moment,
    public adressMember?: string,
    public imageMemberContentType?: string,
    public imageMember?: any,
    public descMember?: string,
    public parentId?: number,
    public memberships?: IMembership[],
    public childId?: number,
    public associationId?: number
  ) {}
}
