export interface IOrganization {
  id?: number;
  code?: string;
  name?: string;
  children?: IOrganization[];
  parent?: IOrganization;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public children?: IOrganization[],
    public parent?: IOrganization
  ) {}
}
