import { Moment } from 'moment';

export interface ICongePolice {
  id?: number;
  numberCongePolice?: string;
  endDateCongePolice?: Moment;
  validCongePolice?: boolean;
  descCongePolice?: string;
  docId?: number;
  vehicleId?: number;
}

export class CongePolice implements ICongePolice {
  constructor(
    public id?: number,
    public numberCongePolice?: string,
    public endDateCongePolice?: Moment,
    public validCongePolice?: boolean,
    public descCongePolice?: string,
    public docId?: number,
    public vehicleId?: number
  ) {
    this.validCongePolice = this.validCongePolice || false;
  }
}
