export interface IGuarding {
  id?: number;
  numberGuarding?: string;
  titleGuarding?: string;
  descGuarding?: string;
  membershipId?: number;
}

export class Guarding implements IGuarding {
  constructor(
    public id?: number,
    public numberGuarding?: string,
    public titleGuarding?: string,
    public descGuarding?: string,
    public membershipId?: number
  ) {}
}
