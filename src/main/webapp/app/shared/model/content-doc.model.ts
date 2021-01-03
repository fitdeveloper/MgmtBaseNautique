export interface IContentDoc {
  id?: number;
  numberContentDoc?: string;
  dataContentDocContentType?: string;
  dataContentDoc?: any;
  docId?: number;
}

export class ContentDoc implements IContentDoc {
  constructor(
    public id?: number,
    public numberContentDoc?: string,
    public dataContentDocContentType?: string,
    public dataContentDoc?: any,
    public docId?: number
  ) {}
}
