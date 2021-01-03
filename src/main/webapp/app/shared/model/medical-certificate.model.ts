import { Moment } from 'moment';

export interface IMedicalCertificate {
  id?: number;
  numberMedicalCertificate?: string;
  endDateMedicalCertificate?: Moment;
  validMedicalCertificate?: boolean;
  descMedicalCertificate?: string;
  docId?: number;
  membershipId?: number;
}

export class MedicalCertificate implements IMedicalCertificate {
  constructor(
    public id?: number,
    public numberMedicalCertificate?: string,
    public endDateMedicalCertificate?: Moment,
    public validMedicalCertificate?: boolean,
    public descMedicalCertificate?: string,
    public docId?: number,
    public membershipId?: number
  ) {
    this.validMedicalCertificate = this.validMedicalCertificate || false;
  }
}
