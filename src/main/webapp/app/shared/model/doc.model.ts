export interface IDoc {
  id?: number;
  numberDoc?: string;
  titleDoc?: string;
  sizeDoc?: number;
  mimeTypeDoc?: string;
  descDoc?: string;
  contentDocId?: number;
  typeDocId?: number;
  insuranceId?: number;
  congePoliceId?: number;
  medicalCertificateId?: number;
  dealershipId?: number;
  membershipId?: number;
}

export class Doc implements IDoc {
  constructor(
    public id?: number,
    public numberDoc?: string,
    public titleDoc?: string,
    public sizeDoc?: number,
    public mimeTypeDoc?: string,
    public descDoc?: string,
    public contentDocId?: number,
    public typeDocId?: number,
    public insuranceId?: number,
    public congePoliceId?: number,
    public medicalCertificateId?: number,
    public dealershipId?: number,
    public membershipId?: number
  ) {}
}
