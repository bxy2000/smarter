import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MenuComponent} from "./menu/menu.component";
import {RoleComponent} from "./role/role.component";
import {UserComponent} from "./user/user.component";
import {OrganizationComponent} from "./organization/organization.component";

const routes: Routes = [
  {path: 'menu', component: MenuComponent},
  {path: 'role', component: RoleComponent},
  {path: 'organization', component: OrganizationComponent},
  {path: 'user', component: UserComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemRoutingModule {
}
