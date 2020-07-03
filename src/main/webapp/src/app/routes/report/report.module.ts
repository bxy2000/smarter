import { NgModule } from '@angular/core';
import {SharedModule} from '@shared/shared.module';

import { ReportRoutingModule } from './report-routing.module';
import { BiComponent } from './bi/bi.component';


@NgModule({
  declarations: [BiComponent],
  imports: [
    SharedModule,
    ReportRoutingModule
  ]
})
export class ReportModule { }
