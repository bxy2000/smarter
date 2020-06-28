import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';

import {SystemRoutingModule} from './system-routing.module';
import {MenuComponent} from './menu/menu.component';
import {RoleComponent} from './role/role.component';
import {UserComponent} from './user/user.component';
import {OrganizationComponent} from './organization/organization.component';


@NgModule({
  declarations: [
    MenuComponent,
    RoleComponent,
    UserComponent,
    OrganizationComponent,
  ],
  imports: [
    SharedModule,
    SystemRoutingModule
  ]
})
export class SystemModule {
}
