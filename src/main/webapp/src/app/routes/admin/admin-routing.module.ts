import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MetricsComponent} from "./metrics/metrics.component";
import {HealthComponent} from "./health/health.component";
import {AuditsComponent} from "./audits/audits.component";

const routes: Routes = [
  {path: 'metrics', component: MetricsComponent},
  {path: 'health', component: HealthComponent},
  {path: 'audits', component: AuditsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
