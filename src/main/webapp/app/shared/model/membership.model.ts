import { Moment } from 'moment';
import { IGuarding } from 'app/shared/model/guarding.model';
import { IDoc } from 'app/shared/model/doc.model';
import { IMedicalCertificate } from 'app/shared/model/medical-certificate.model';
import { IDealership } from 'app/shared/model/dealership.model';
import { IVehicle } from 'app/shared/model/vehicle.model';

export interface IMembership {
  id?: number;
  numberMembership?: string;
  amountMembership?: number;
  startDateMembership?: Moment;
  endDateMembership?: Moment;
  validMembership?: boolean;
  descMembership?: string;
  guardings?: IGuarding[];
  docs?: IDoc[];
  medicalCertificates?: IMedicalCertificate[];
  dealerships?: IDealership[];
  vehicles?: IVehicle[];
  memberId?: number;
}

export class Membership implements IMembership {
  constructor(
    public id?: number,
    public numberMembership?: string,
    public amountMembership?: number,
    public startDateMembership?: Moment,
    public endDateMembership?: Moment,
    public validMembership?: boolean,
    public descMembership?: string,
    public guardings?: IGuarding[],
    public docs?: IDoc[],
    public medicalCertificates?: IMedicalCertificate[],
    public dealerships?: IDealership[],
    public vehicles?: IVehicle[],
    public memberId?: number
  ) {
    this.validMembership = this.validMembership || false;
  }
}
