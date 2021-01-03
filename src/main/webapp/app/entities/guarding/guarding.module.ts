import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { GuardingComponent } from './guarding.component';
import { GuardingDetailComponent } from './guarding-detail.component';
import { GuardingUpdateComponent } from './guarding-update.component';
import { GuardingDeleteDialogComponent } from './guarding-delete-dialog.component';
import { guardingRoute } from './guarding.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(guardingRoute)],
  declarations: [GuardingComponent, GuardingDetailComponent, GuardingUpdateComponent, GuardingDeleteDialogComponent],
  entryComponents: [GuardingDeleteDialogComponent],
})
export class MgmtBaseNautiqueGuardingModule {}
