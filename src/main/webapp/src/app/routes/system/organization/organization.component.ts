import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ReuseTabService} from "@delon/abc";
import {Organization} from "@core/model/organization.model";
import {OrganizationService} from "@core/service/organization.service";
import {IMenu} from "@core/model/menu.model";
import {convertListToTree} from "@core/utils/tree-util";

@Component({
  selector: 'app-organization',
  templateUrl: './organization.component.html',
  styles: []
})
export class OrganizationComponent implements OnInit {
  data = [];
  expandDataCache = {};

  constructor(
    private organizationService: OrganizationService,
    private reuseTabService: ReuseTabService,) {
  }

  ngOnInit() {
    this.reuseTabService.title = '组织机构';
    this.load();
  }

  load() {
    this.organizationService.query()
      .subscribe((res: HttpResponse<IMenu[]>) => {
        const result = [];

        convertListToTree(result, res.body, null, null);
        this.data = result;
        this.data.forEach(item => {
          this.expandDataCache[item.id] = this.convertTreeToList(item);
        });
      })
  }

  collapse(array: IMenu[], data: IMenu, $event: boolean): void {
    if ($event === false) {
      if (data.children) {
        data.children.forEach(d => {
          const target = array.find(a => a.id === d.id);
          target['expand'] = false;
          this.collapse(array, target, false);
        });
      } else {
        return;
      }
    }
  }

  convertTreeToList(root: object): IMenu[] {
    const stack = [];
    const array = [];
    const hashMap = {};
    stack.push({...root, level: 0, expand: false});

    while (stack.length !== 0) {
      const node = stack.pop();
      this.visitNode(node, hashMap, array);
      if (node.children) {
        for (let i = node.children.length - 1; i >= 0; i--) {
          stack.push({...node.children[i], level: node.level + 1, expand: false, parent: node});
        }
      }
    }

    return array;
  }

  visitNode(node: IMenu, hashMap: object, array: IMenu[]): void {
    if (!hashMap[node.id]) {
      hashMap[node.id] = true;
      array.push(node);
    }
  }
}
