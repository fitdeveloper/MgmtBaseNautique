import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { CongePoliceComponent } from './conge-police.component';
import { CongePoliceDetailComponent } from './conge-police-detail.component';
import { CongePoliceUpdateComponent } from './conge-police-update.component';
import { CongePoliceDeleteDialogComponent } from './conge-police-delete-dialog.component';
import { congePoliceRoute } from './conge-police.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(congePoliceRoute)],
  declarations: [CongePoliceComponent, CongePoliceDetailComponent, CongePoliceUpdateComponent, CongePoliceDeleteDialogComponent],
  entryComponents: [CongePoliceDeleteDialogComponent],
})
export class MgmtBaseNautiqueCongePoliceModule {}
