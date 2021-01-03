export interface ITypeDoc {
  id?: number;
  numberTypeDoc?: string;
  titleTypeDoc?: string;
  descTypeDoc?: string;
  docId?: number;
}

export class TypeDoc implements ITypeDoc {
  constructor(
    public id?: number,
    public numberTypeDoc?: string,
    public titleTypeDoc?: string,
    public descTypeDoc?: string,
    public docId?: number
  ) {}
}
