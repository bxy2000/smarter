import {Component, OnInit, ViewChild} from '@angular/core';
import {NzFormatEmitEvent, NzMessageService} from 'ng-zorro-antd';
import {RoleService} from "@core/service/role.service";
import {MenuService} from "@core/service/menu.service";
import {convertListToTree, treeForEach} from "@core/utils/tree-util";
import {ReuseTabService} from "@delon/abc";

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.less'],
})
export class RoleComponent implements OnInit {
  @ViewChild('menuTree',{read:true,static:true})
  menuTree
  // 窗体状态: 0 浏览 1 新增 2编辑
  formStatus = 0;
  // 角色
  editCache = {};
  dataSet = [];
  currentId = -1;
  total = 0;
  // 权限（菜单）节点集合
  nodes = [];
  checkedRoles: string[] = [];
  constructor(
    private roleService: RoleService,
    private menuService: MenuService,
    private messageService: NzMessageService,
    private reuseTabService: ReuseTabService,
  ) {
  }

  ngOnInit(): void {
    this.reuseTabService.title = '角色资源';
    this.loadMenus();
  }

  loadMenus() {
    this.menuService.query().subscribe(
      (res) => {
        const menus = res.body;
        const result = [];
        // 把list转换成树形结构
        convertListToTree(result, menus, null, u => {
          u.key = u.id+'';
          u.title=u.text;
          u.data= u;
          if(u.id == 10100){
            u.disabled = true;
          }
        });
        // 计算树的叶子节点
        treeForEach(result, u=>{
          u.isLeaf = false;
          if(u.children && u.children.length===0){
            u.isLeaf = true;
          }
        });
        this.nodes = result;

        this.loadRoles();
      }
    )
  }

  loadRoles() {
    this.roleService.query({
      page: 0,
      size: 10000,
      sort: ['id,asc'],
    }).subscribe(
      (res) => {
        this.dataSet = res.body;
        if(this.dataSet != null) {
          this.total = this.dataSet.length;
        }
        this.updateEditCache();
        if(this.dataSet && this.dataSet.length>0){
          if(this.currentId == -1) {
            this.currentId = 0;
          }
          this.selectCurrentRow(this.dataSet[this.currentId]);
        }
      }
    )
  }

  selectCurrentRow(data: any) {
    if(data == null || data.id == null) {
      return;
    }
    this.currentId = data.id;
    this.roleService.find(data.id).subscribe((res)=>{
      this.checkedRoles = res.body.menus.map(u=>u.id+'');

      if(this.checkedRoles.findIndex(u=>u == '10100') < 0) {
        this.checkedRoles= ['10100', ...this.checkedRoles];
      }
    })
  }

  saveMenu(){
    if(this.currentId == -1){
      this.messageService.info('请选择角色！');
      return;
    }

    const index = this.dataSet.findIndex(u=>u.id === this.currentId);

    const role = this.dataSet[index];

    role.menus = [];

    treeForEach(this.nodes, u=>{
      if(u.checked && u.isLeaf){
        role.menus.push({id: +u.key});
      }
    });

    this.roleService.update(role).subscribe((res)=>{
      const role = res.body;
      this.editCache[role.id].data.menus = role.menus;

      this.messageService.success('保存成功！');
    },(res)=>{
      this.messageService.error('保存失败！')
    });
  }

  /**
   * 新增
   */
  addRow(): void {
    this.formStatus = 1;
    this.currentId = this.getLastId()+1;
    this.dataSet = [...this.dataSet, {
      id: this.currentId,
      roleName: '',
      menus: [{id:10100}]
    }];
    this.updateEditCache();
    this.editCache[this.currentId].edit = true;
  }

  getLastId(): number {
    let result = 0;
    if(this.dataSet.length>1) {
      result = this.dataSet[this.dataSet.length-1].id;
    }
    return result;
  }

  /**
   * 删除
   * @param i
   */
  deleteRow(id: string): void {
    this.roleService.delete(+id).subscribe(
      () => {
        this.loadRoles();
        this.messageService.success('删除角色成功！')
      },
      () => {
        this.messageService.error('删除角色失败！')
      }
    )
  }

  /**
   * 开始编辑
   * @param id
   */
  startEdit(id: string): void {
    this.formStatus = 2;
    this.editCache[id].edit = true;
  }

  // 放弃修改
  cancelEdit(id: string): void {
    if (this.formStatus === 1) {
      this.dataSet.splice(this.dataSet.length-1, 1);

      this.editCache = {};
      this.updateEditCache();
    } else {
      this.editCache[id].edit = false;
    }

    this.formStatus = 0;
  }
  /**
   * 保存编辑
   * @param id
   */
  finishEdit(id: string): void {
    const role = {
      id: this.editCache[id].data.id,
      roleName: this.editCache[id].data.roleName,
      menus: this.editCache[id].data.menus
    }

    if(role.roleName == "") {
      this.messageService.warning('请输入角色名');
      return;
    }

    if(role.roleName.length > 50) {
      this.messageService.warning('角色名长度不能超过50位！');
      return;
    }

    if (this.formStatus == 1) {
      role.id = null;
      // 新增
      this.roleService.create(role).subscribe(
        (res) => {
          this.currentId = res.body.id;
          this.loadRoles();
          this.formStatus = 0;
          this.messageService.success('新增角色成功！');
        },(res)=>{
          if(res.error.detail.indexOf('ux_role_role_name')>=0){
            this.messageService.error('角色名重复');
          } else {
            this.messageService.error('新增角色失败！');
          }
        }
      )
    } else {
      // 编辑
      this.roleService.update(role).subscribe(
        (res) => {
          this.editCache[id].data = res.body;
          const index = this.dataSet.findIndex(item => item.id === id);
          Object.assign(this.dataSet[index], this.editCache[id].data);
          this.editCache[id].edit = false;
          this.formStatus = 0;
          this.messageService.success('编辑角色成功！');
        },(res)=> {
          if (res.error.detail.indexOf('ux_role_role_name') >= 0) {
            this.messageService.error('角色名重复');
          } else {
            this.messageService.error('编辑角色失败！');
          }
        }
      )
    }
  }

  updateEditCache(): void {
    this.editCache = {};
    this.dataSet.forEach(item => {
      if (!this.editCache[item.id]) {
        this.editCache[item.id] = {
          edit: false,
          data: {...item}
        };
      }
    });
  }
}
