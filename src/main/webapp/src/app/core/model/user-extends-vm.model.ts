import { IUser } from './user.model';
import { IOrganization } from './organization.model';
import { IRole } from './role.model';
import { Gender } from './enum/gender';
import {IUserExtends} from "@core/model/user-extends.model";

export interface IUserExtendsVM extends IUserExtends{
  password?: string;
}

export class UserExtendsVM implements IUserExtendsVM {
  constructor(
    public id?: number,
    public username?: string,
    public gender?: Gender,
    public mobile?: string,
    public user?: IUser,
    public dataPermission?: IOrganization,
    public roles?: IRole[],
    public password?: string
  ) {
  }

}
