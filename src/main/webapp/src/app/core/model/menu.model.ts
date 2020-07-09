import {IRole} from './role.model';

export interface IMenu {
  id?: number;
  text?: string;
  group?: boolean;
  link?: string;
  externalLink?: string;
  target?: string;
  icon?: string;
  hide?: boolean;
  description?: string;
  menuType?: number;
  menuLink?: string;
  menuOrder?: number;
  menuHeight?: number;
  children?: IMenu[];
  parent?: IMenu;
  roles?: IRole[];
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public text?: string,
    public group?: boolean,
    public link?: string,
    public externalLink?: string,
    public target?: string,
    public icon?: string,
    public hide?: boolean,
    public description?: string,
    public menuType?: number,
    public menuLink?: string,
    public menuOrder?: number,
    public menuHeight?: number,
    public children?: IMenu[],
    public parent?: IMenu,
    public roles?: IRole[]
  ) {
    this.group = this.group || false;
    this.hide = this.hide || false;
  }
}
