export interface IMenuDTO {
  text: string;
  id?: number;
  group?: boolean;
  link?: string;
  externalLink?: string;
  target?: '_blank' | '_self' | '_parent' | '_top';
  icon?: string;
  hide?: boolean;
  description?: string;
  children?: IMenuDTO[];
}

export class MenuDTO implements IMenuDTO {
  constructor(
    public text: string,
    public id?: number,
    public group?: boolean,
    public link?: string,
    public externalLink?: string,
    public target?: '_blank' | '_self' | '_parent' | '_top',
    public icon?: string,
    public hide?: boolean,
    public description?: string,
    public children?: IMenuDTO[],
  ) {}
}

