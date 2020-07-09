import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {IMenu} from "@core/model/menu.model";
import {MenuService} from "../../../core/service/menu.service";
import {convertListToTree} from '@core/utils/tree-util';
import {ReuseTabService} from "@delon/abc";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NzContextMenuService, NzMessageService, NzTreeComponent, NzTreeNode, NzFormatEmitEvent} from 'ng-zorro-antd';
import {IRole} from "@core/model/role.model";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styles: []
})
export class MenuComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private nzContextMenuService: NzContextMenuService,
    private reuseTabService: ReuseTabService,
    private messageService: NzMessageService,
    private menuService: MenuService,
  ) {
  }

  @ViewChild('nzTreeComponent', {static: false}) nzTreeComponent: NzTreeComponent;

  // 数据目录导航树节点
  nodes = [];
  // searchValue = '';
  // 选中的节点
  selectedNode = [];

  // 表单验证
  validateForm: FormGroup;

  // 新增对话框=========================================
  isVisible = false;
  modalTitle = "";
  inputNodeTitle = "";
  operation = 0;

  // 删除对话框====================================================
  deleteIsVisible = false;

  ngOnInit() {
    this.validateForm = this.fb.group({
      id: [null, [Validators.required]],
      text: [null, Validators.required],
      menuType: ['0', [Validators.required]],
      menuLink: ['', [Validators.required]],
      menuOrder: [1],
      menuHeight: [800]
    });

    this.reuseTabService.title = '导航菜单';
    this.loadMenu();
  }

  loadMenu(): void {
    this.menuService.query().subscribe((res: HttpResponse<IMenu[]>) => {
      const result = [];

      const menu = res.body.filter(u => u.menuOrder === 1);
      let parentID = null;

      if (menu && menu.length > 0) {
        parentID = menu[0].parent.id;
      }

      convertListToTree(result, menu, parentID, u => {
        u.key = u.id;
        u.title = u.text;
        u.origin = u;
      });

      this.nodes = result;

      if (this.nodes != null && this.nodes.length >= 1) {
        this.selectedNode = [this.nodes[0].key];
        this.resetForm(this.nodes[0]);
      }
    })
  }

  nzClick(event: NzFormatEmitEvent): void {
    if (!event || !event.node || !event.node.origin)
      return;

    this.resetForm(event.node);
  }

  resetForm(node): void {
    this.validateForm.setValue({
      id: node.key,
      text: node.title,
      menuType: node.origin.menuType + '',
      menuLink: node.origin.menuLink,
      menuOrder: node.origin.menuOrder,
      menuHeight: node.origin.menuHeight
    });
  }

  createNode(operation: number): void {
    const titles = ['新增分组', '新增内部链接', '新增外部链接'];
    this.isVisible = true;
    this.modalTitle = titles[operation];
    this.inputNodeTitle = "";
    this.operation = operation;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  handleOk(): void {
    const nodeList = this.nzTreeComponent.getSelectedNodeList();

    switch (this.operation) {
      case 0:
        this.newNode(1, (data) => {
          this.nodes = [...this.nodes, data];
        });
        break;
      case 1:
      case 2:
        if (nodeList == null || nodeList.length < 1) {
          this.messageService.info('请选中分组');
          break;
        }
        const node = nodeList[0];
        if (+node.origin.menuType != 0) {
          this.messageService.info('请选中分组');
          break;
        }
        this.newNode(+node.key, (data) => {
          node.addChildren([data]);
        });
    }
    this.isVisible = false;
  }

  newNode(parentId, callback): void {
    const node: IMenu = {
      id: null,
      text: this.inputNodeTitle,
      group: this.operation === 0,
      link: this.operation === 0 ? '/report' : '',
      externalLink: '',
      target: this.operation === 2 ? '_blank' : '',
      icon: '',
      hide: false,
      description: '',
      menuType: this.operation,
      menuLink: this.operation === 0 ? '/report' : '',
      menuOrder: 1,
      menuHeight: 800,
      children: [],
      parent: parentId == null ? null : {id: parentId},
      roles: [],
    };
    this.menuService.create(node).subscribe((res) => {
      res.body.key = res.body.id;
      res.body.title = res.body.text;
      res.body.origin = res.body;
      callback(res.body);
      this.messageService.success('新增成功');
    }, (res) => {
      this.messageService.error('新增失败！<br />' + res.error.title);
    });
  }

  deleteNode(): void {
    this.deleteIsVisible = true;
  }

  deleteHandleCancel(): void {
    this.deleteIsVisible = false;
  }

  deleteHandleOk(): void {
    const nodeList = this.nzTreeComponent.getSelectedNodeList();

    if (nodeList != null && nodeList.length > 0) {
      this.deleteMenu(nodeList.map(u => u.origin.key), () => {
        this.loadMenu();
      })
    }

    this.deleteIsVisible = false;
  }

  deleteMenu(id, callback): void {
    this.menuService.delete(+id).subscribe((res) => {
      callback();
      this.messageService.success('删除成功！')
    }, (res) => {
      this.messageService.error('删除失败！');
    })
  }

  // 基本信息========================================================
  submitForm(): void {
    const nodeList = this.nzTreeComponent.getSelectedNodeList();

    if (nodeList == null || nodeList.length != 1) {
      this.messageService.info('请选中要修改的菜单项');
      return;
    }
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    if (!this.validateForm.valid) {
      return;
    }

    const node = nodeList[0];

    this.updateNode(node, data => {
      node.title = data.title;
      node.origin.tableName = data.tableName;
      node.origin.remarks = data.remarks;
      node.origin.relationGraph = data.relationGraph;
    });
  }

  updateNode(data, callback): void {
    const node = {
      id: +data.key,
      text: this.validateForm.controls.text.value,
      group: this.operation === 0,
      link: this.operation === 0 ? '/report' : '',
      externalLink: '',
      target: this.operation === 2 ? '_blank' : '',
      icon: '',
      hide: false,
      description: '',
      menuType: this.validateForm.controls.menuType.value,
      menuLink: this.validateForm.controls.menuLink.value,
      menuOrder: this.validateForm.controls.menuOrder.value,
      menuHeight: this.validateForm.controls.menuHeight.value,
      children: [],
      roles: [],
      parent: data.origin.parent,
    };
    switch (+node.menuType) {
      case 0:
        node.menuLink = '/report';
        node.link = '/report';
        node.externalLink = '';
        node.target = '';
        node.icon = 'anticon-link'
        break;
      case 1:
        node.link = '/report/bi/' + node.id;
        node.externalLink = '';
        node.target = '';
        break;
      case 2:
        node.link = '';
        node.externalLink = node.menuLink;
        node.target = '_blank';
        break;
    }
    this.menuService.update(node).subscribe((res) => {
      res.body.key = res.body.id;
      res.body.title = res.body.text;
      res.body.origin = res.body;
      callback(res.body);
      this.messageService.success('修改成功！');
    }, (res) => {
      this.messageService.error('修改失败！<br />' + res.error.title);
    });
  }
}
