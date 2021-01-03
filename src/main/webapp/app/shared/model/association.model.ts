import { IMember } from 'app/shared/model/member.model';

export interface IAssociation {
  id?: number;
  numberAssociation?: string;
  nameAssociation?: string;
  imageAssociationContentType?: string;
  imageAssociation?: any;
  descAssociation?: string;
  members?: IMember[];
}

export class Association implements IAssociation {
  constructor(
    public id?: number,
    public numberAssociation?: string,
    public nameAssociation?: string,
    public imageAssociationContentType?: string,
    public imageAssociation?: any,
    public descAssociation?: string,
    public members?: IMember[]
  ) {}
}
