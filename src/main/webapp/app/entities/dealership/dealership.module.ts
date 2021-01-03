import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { DealershipComponent } from './dealership.component';
import { DealershipDetailComponent } from './dealership-detail.component';
import { DealershipUpdateComponent } from './dealership-update.component';
import { DealershipDeleteDialogComponent } from './dealership-delete-dialog.component';
import { dealershipRoute } from './dealership.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(dealershipRoute)],
  declarations: [DealershipComponent, DealershipDetailComponent, DealershipUpdateComponent, DealershipDeleteDialogComponent],
  entryComponents: [DealershipDeleteDialogComponent],
})
export class MgmtBaseNautiqueDealershipModule {}
