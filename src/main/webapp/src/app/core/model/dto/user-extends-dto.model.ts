import {Gender} from "@core/model/enum/gender";
import {IOrganization} from "@core/model/organization.model";

export interface IUserExtendsDTO {
  id?: number;
  username?: string;
  gender?: Gender;
  idCardNo?: string;
  mobile?: string;
  contactName?: string;
  contactMobile?: number;
  admin?: boolean;
  grade?: string;
  organization?: IOrganization;
  dataPermission?: IOrganization;
  roles?: string[];
}

export class UserExtendsDTO implements IUserExtendsDTO {
  constructor(
    public id?: number,
    public username?: string,
    public gender?: Gender,
    public idCardNo?: string,
    public mobile?: string,
    public contactName?: string,
    public contactMobile?: number,
    public admin?: boolean,
    public grade?: string,
    public organization?: IOrganization,
    public dataPermission?: IOrganization,
    public roles?: string[]
  ) {
  }
}
