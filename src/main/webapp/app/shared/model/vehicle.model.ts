import { ICongePolice } from 'app/shared/model/conge-police.model';
import { IInsurance } from 'app/shared/model/insurance.model';

export interface IVehicle {
  id?: number;
  numberVehicle?: string;
  titleVehicle?: string;
  descVehicle?: string;
  congePolices?: ICongePolice[];
  insurances?: IInsurance[];
  membershipId?: number;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public numberVehicle?: string,
    public titleVehicle?: string,
    public descVehicle?: string,
    public congePolices?: ICongePolice[],
    public insurances?: IInsurance[],
    public membershipId?: number
  ) {}
}
