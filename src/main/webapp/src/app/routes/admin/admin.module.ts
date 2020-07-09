import { NgModule } from '@angular/core';
import {SharedModule} from "@shared";

import { AdminRoutingModule } from './admin-routing.module';
import { MetricsComponent } from './metrics/metrics.component';
import { HealthComponent } from './health/health.component';
import { AuditsComponent } from './audits/audits.component';


@NgModule({
  declarations: [
    MetricsComponent,
    HealthComponent,
    AuditsComponent
  ],
  imports: [
    SharedModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
