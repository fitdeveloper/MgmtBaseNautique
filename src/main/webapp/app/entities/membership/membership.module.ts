import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { MembershipComponent } from './membership.component';
import { MembershipDetailComponent } from './membership-detail.component';
import { MembershipUpdateComponent } from './membership-update.component';
import { MembershipDeleteDialogComponent } from './membership-delete-dialog.component';
import { membershipRoute } from './membership.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(membershipRoute)],
  declarations: [MembershipComponent, MembershipDetailComponent, MembershipUpdateComponent, MembershipDeleteDialogComponent],
  entryComponents: [MembershipDeleteDialogComponent],
})
export class MgmtBaseNautiqueMembershipModule {}
