import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';

import { MetricsComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [MetricsComponent],
})
export class MetricsModule {}
