import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { AssociationComponent } from './association.component';
import { AssociationDetailComponent } from './association-detail.component';
import { AssociationUpdateComponent } from './association-update.component';
import { AssociationDeleteDialogComponent } from './association-delete-dialog.component';
import { associationRoute } from './association.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(associationRoute)],
  declarations: [AssociationComponent, AssociationDetailComponent, AssociationUpdateComponent, AssociationDeleteDialogComponent],
  entryComponents: [AssociationDeleteDialogComponent],
})
export class MgmtBaseNautiqueAssociationModule {}
