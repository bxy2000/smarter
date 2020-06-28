import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {IMenu} from "@core/model/menu.model";
import {MenuService} from "../../../core/service/menu.service";
import {convertListToTree} from '@core/utils/tree-util';
import {ReuseTabService} from "@delon/abc";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styles: []
})
export class MenuComponent implements OnInit {
  data = [];
  expandDataCache = {};

  constructor(
    private menuService: MenuService,
    private reuseTabService: ReuseTabService,
  ) {
  }

  ngOnInit(): void {
    this.reuseTabService.title = '全局资源';
    this.load();
  }

  load() {
    this.menuService.query()
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
          target.expand = false;
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
