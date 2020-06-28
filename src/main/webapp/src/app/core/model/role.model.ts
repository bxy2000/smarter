import { IMenu } from './menu.model';
import { IUserExtends } from './user-extends.model';

export interface IRole {
  id?: number;
  roleName?: string;
  menus?: IMenu[];
  userExtends?: IUserExtends[];
}

export class Role implements IRole {
  constructor(public id?: number, public roleName?: string, public menus?: IMenu[], public userExtends?: IUserExtends[]) {}
}
