import { IUser } from './user.model';
import { IOrganization } from './organization.model';
import { IRole } from './role.model';
import { Gender } from './enum/gender';

export interface IUserExtends {
  id?: number;
  username?: string;
  gender?: Gender;
  mobile?: string;
  user?: IUser;
  dataPermission?: IOrganization;
  roles?: IRole[];
}

export class UserExtends implements IUserExtends {
  constructor(
    public id?: number,
    public username?: string,
    public gender?: Gender,
    public mobile?: string,
    public user?: IUser,
    public dataPermission?: IOrganization,
    public roles?: IRole[]
  ) {
  }

}
