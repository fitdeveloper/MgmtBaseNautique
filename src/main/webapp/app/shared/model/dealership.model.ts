import { Moment } from 'moment';

export interface IDealership {
  id?: number;
  numberDealership?: string;
  endDateDealership?: Moment;
  validDealership?: boolean;
  descDealership?: string;
  docId?: number;
  membershipId?: number;
}

export class Dealership implements IDealership {
  constructor(
    public id?: number,
    public numberDealership?: string,
    public endDateDealership?: Moment,
    public validDealership?: boolean,
    public descDealership?: string,
    public docId?: number,
    public membershipId?: number
  ) {
    this.validDealership = this.validDealership || false;
  }
}
