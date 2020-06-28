import {Component, OnInit} from '@angular/core';
import {UserExtendsExtService} from "@core/service/customized/user-extends-ext.service";
import {IUserExtends} from "@core/model/user-extends.model";
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {NzMessageService} from 'ng-zorro-antd';
import {IRole, Role} from "@core/model/role.model";
import {RoleService} from "@core/service/role.service";
import {ReuseTabService} from "@delon/abc";
import {OrganizationService} from "@core/service/organization.service";
import {convertListToTree} from "@core/utils/tree-util";
import {IOrganization, Organization} from "@core/model/organization.model";
import {Gender} from "@core/model/enum/gender";
import {IUserExtendsVM} from "@core/model/user-extends-vm.model";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['user.component.less'],
})
export class UserComponent implements OnInit {
  // 编辑器状态：0 浏览 1 新增 2 修改
  formStatus = 0;
  // 编辑缓存
  editCache: { [key: string]: { edit: boolean; data: IUserExtendsVM; } } = {};
  // 数据集
  dataSet: IUserExtendsVM[] = [];
  // 角色字典
  roles: IRole[] = [];

  roleIds: number[] = [];

  // 页码
  pageIndex = 1;
  // 每页大小
  pageSize = 20;
  // 总记录数
  total = 0;

  organizationTree = [];

  constructor(
    private roleService: RoleService,
    private userExtendsExtService: UserExtendsExtService,
    private messageService: NzMessageService,
    private organizationService: OrganizationService,
    private reuseTabService: ReuseTabService,
  ) {
    this.reuseTabService.title = '用户管理';
  }

  ngOnInit(): void {
    this.loadRoles();
  }

  /**
   * 加载角色信息
   */
  loadRoles() {
    this.roleService.query({
      page: 0,
      size: 10000,
      sort: ['id,asc'],
    }).subscribe(
      (res) => {
        this.roles = res.body;
        this.loadOrganizations();
      }
    )
  }

  /**
   * 组织机构
   */
  loadOrganizations() {
    this.organizationService.query({
      page: 0,
      size: 100000,
      sort: ['id,asc']
    }).subscribe(
      (res) => {
        convertListToTree(this.organizationTree, res.body, null, u => {
          u.key = u.id;
          u.title = u.code + ' ' + u.name;
        });
        this.loadUserExtends();
      }
    )
  }

  /**
   * 加载用户扩展数据
   */
  loadUserExtends() {
    this.userExtendsExtService.query({
      page: this.pageIndex - 1,
      size: this.pageSize,
      sort: ['id,asc'],
      'login': '',
      'username': '',
      'organizationCode': ''
    }).subscribe(
      (res: HttpResponse<IUserExtends[]>) => {
        this.total = parseInt(res.headers.get('X-Total-Count'));
        this.dataSet = res.body;
        console.log(this.dataSet);
        this.updateEditCache();
      },
      (res: HttpErrorResponse) => {
        this.messageService.error(res.message)
      }
    )
  }

  /**
   * 新增
   */
  addRow(): void {
    this.formStatus = 1;
    this.dataSet = [{
      id: 0,
      username: '',
      password: '123456',
      gender: Gender.MALE,
      mobile: '',
      user: {
        id: null,
        login: '',
        email: ''
      },
      roles: [],
      dataPermission: null,// {},//{id: null, code: null, name: null},
    },
      ...this.dataSet
    ];

    this.roleIds = [];

    this.updateEditCache();
    this.editCache[0].edit = true;
  }

  // 删除数据
  deleteRow(id: string): void {
    this.userExtendsExtService.delete(+id).subscribe(
      () => {
        this.loadUserExtends();
        this.messageService.success('删除用户成功！')
      },
      () => {
        this.messageService.error('删除用户失败！')
      }
    )
  }

  // 开始修改
  startEdit(id: string): void {
    this.formStatus = 2;

    this.roleIds = this.editCache[id].data.roles != null ? this.editCache[id].data.roles.map(u => u.id) : [];

      this.editCache[id].edit = true;
  }

  // 放弃修改
  cancelEdit(id: string): void {
    console.log("id:" + id);
    const index = this.dataSet.findIndex(item => item.id == +id);
    console.log("index:"+index);

    if (this.formStatus === 1) {
      console.log('begin splice=========');
      this.dataSet.splice(0, 1);
      console.log('end splice===========');
      console.log("editCache:" + this.editCache);
      // this.editCache[id] = {};
      console.log("endCache:" + this.editCache);
      console.log("dataSet" + this.dataSet);
      this.updateEditCache();
      console.log("dateSet" + this.dataSet);
      console.log("dkkkkkkkkkkkkkkkkkk")
    } else {
      this.editCache[id] = {
        data: {...this.dataSet[index]},
        edit: false
      }
    }

    this.roleIds = [];

    this.formStatus = 0;
  }

  /**
   * 保存
   * @param id
   */
  saveEdit(id: string): void {
    if (this.editCache[id].data.user.login == null || this.editCache[id].data.user.login == '') {
      this.messageService.warning('请输入登录名');
      return;
    }

    if (this.editCache[id].data.user.login.length < 4) {
      this.messageService.warning('登录名长度不能小于4');
      return;
    }

    const loginPattern = /^[a-zA-Z0-9]*$/;
    if (!loginPattern.test(this.editCache[id].data.user.login)) {
      this.messageService.warning('登录名只能是字母或数字');
      return;
    }

    if (this.editCache[id].data.username == null || this.editCache[id].data.username == '') {
      this.messageService.warning('请输入姓名！')
      return;
    }

    if (this.editCache[id].data.user.email == null || this.editCache[id].data.user.email == '') {
      this.messageService.warning('请输入邮箱！');
      return;
    }

    const emailPattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    if (!emailPattern.test(this.editCache[id].data.user.email)) {
      this.messageService.warning('邮箱格式错误！');
      return;
    }

    const index = this.dataSet.findIndex(item => item.id == +id);

    this.editCache[id].data.roles = this.roleIds != null ?
       this.roleIds.map(u => new Role(u, null, null, null)) : [];

    // this.editCache[id].data.dataPermission = {id: this.editCache[id].data.dataPermissionId}

    if (this.formStatus === 1) {
      this.editCache[id].data.id = null;
      this.userExtendsExtService.create(this.editCache[id].data).subscribe(
        (res) => {
          this.dataSet[0] = res.body;
          this.updateEditCache();
          this.formStatus = 0;
          this.messageService.success('新增用户成功！');

          this.loadUserExtends();
        },
        (res) => {
          this.messageService.error('新增用户失败！<br />' + res.error.title);
        }
      );
    } else if (this.formStatus === 2) {
      // this.editCache[id].data.dataPermission = {id: this.editCache[id].data.dataPermissionId}

      console.log(this.editCache[id].data);

      this.userExtendsExtService.update(this.editCache[id].data).subscribe(
        (res) => {
          this.editCache[id].data = res.body;
          Object.assign(this.dataSet[index], this.editCache[id].data);
          this.editCache[id].edit = false;
          this.updateEditCache();
          this.formStatus = 0;
          this.messageService.success('编辑用户成功！')
        },
        () => {
          // 错误！
          this.messageService.error('编辑用户失败！')
        }
      )
    }
  }

  /**
   * 更新编辑状态
   */

  updateEditCache(): void {
    this.dataSet.forEach(item => {
      // if (!this.editCache[item.id]) {
      this.editCache[item.id] = {
        edit: false,
        data: {...item}
      };
      // }
      this.updateEditCacheRoles(this.editCache[item.id].data);
    });
  }

  updateEditCacheRoles(data: any) {
    if (data.roles) {
      data.roleIds = data.roles ? data.roles.map(u => u.id) : [];
    } else {
      data.roleIds = [];
    }
  }

  /**
   * 显示角色
   * @param roles
   */
  dispRole(roles: IRole[]) {
    const result = roles ? roles.map(u => u.roleName).join(',') : '';
    return result;
  }

  /**
   * 重置密码
   */
  resetPassword(login: string) {
    this.userExtendsExtService.resetPassword(login).subscribe(() => {
      this.messageService.success('重置密码成功！');
    }, () => {
      this.messageService.error('重置密码失败！');
    });
  }
}
