import { Moment } from 'moment';

export interface IInsurance {
  id?: number;
  numberInsurance?: string;
  endDateInsurance?: Moment;
  validInsurance?: boolean;
  descInsurance?: string;
  docId?: number;
  vehicleId?: number;
}

export class Insurance implements IInsurance {
  constructor(
    public id?: number,
    public numberInsurance?: string,
    public endDateInsurance?: Moment,
    public validInsurance?: boolean,
    public descInsurance?: string,
    public docId?: number,
    public vehicleId?: number
  ) {
    this.validInsurance = this.validInsurance || false;
  }
}
