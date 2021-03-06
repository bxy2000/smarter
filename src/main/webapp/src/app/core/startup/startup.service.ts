import { Injectable, Injector, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MenuService, SettingsService, TitleService, ALAIN_I18N_TOKEN } from '@delon/theme';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { ACLService } from '@delon/acl';

import { NzIconService } from 'ng-zorro-antd/icon';
import { ICONS_AUTO } from '../../../style-icons-auto';
import { ICONS } from '../../../style-icons';
import {AccountExtService} from "@core/service/customized/account-ext.service";

/**
 * Used for application startup
 * Generally used to get the basic data of the application, like: Menu Data, User Data, etc.
 */
@Injectable()
export class StartupService {
  getQueryVariable(variable): string {
    const query = window.location.href.split("?");

    if(query.length != 2) {
      return null;
    }

    const vars = query[1].split("&");

    for (let i = 0; i < vars.length; i++) {
      const pair = vars[i].split("=");
      if (pair[0] == variable) {
        return pair[1];
      }
    }
    return null;
  }

  constructor(
    iconSrv: NzIconService,
    private menuService: MenuService,
    private settingService: SettingsService,
    private aclService: ACLService,
    private titleService: TitleService,
    @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
    private httpClient: HttpClient,
    private injector: Injector,
    private accountExtService: AccountExtService
  ) {
    iconSrv.addIcon(...ICONS_AUTO, ...ICONS);
  }

  private viaHttp(resolve: any, reject: any) {
    const login = this.getQueryVariable("userId");

    if(login != null && login != "") {
      this.injector.get(Router).navigateByUrl('/passport/login?id=' + login);
      resolve({});
      return;
    }

    const tokenData = this.tokenService.get();
    if (!tokenData.token) {
      this.injector.get(Router).navigateByUrl('/passport/login');
      resolve({});
      return;
    }

    const app: any = {
      name: `BI 门户`,
      description: `BI 门户`
    };
    const user: any = {
      name: tokenData.name,
      avatar: './assets/tmp/img/avatar.jpg',
      email: 'admin@gilight.cn',
      token: tokenData.token,
      user: null
    };
    // Application information: including site name, description, year
    this.settingService.setApp(app);
    // User information: including name, avatar, email address
    this.settingService.setUser(user);
    // ACL: Set the permissions to full, https://ng-alain.com/acl/getting-started
    this.aclService.setFull(true);
    // Menu data, https://ng-alain.com/theme/menu

    // console.log("username:" + user.name);

    this.accountExtService.getMenu(user.name).subscribe((menu) => {
      // console.log(menu);
      this.menuService.clear();
      this.menuService.add(menu);
      // console.log(menu);
    });

    this.accountExtService.getUserExtends(user.name).subscribe((userExtends)=>{
      user.user = userExtends;
      this.settingService.setUser(user);
      // console.log(user.user);
    });
    // Can be set page suffix title, https://ng-alain.com/theme/title
    this.titleService.suffix = app.name;

    resolve({});
  }

  load(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.viaHttp(resolve, reject);
    });
  }
}
