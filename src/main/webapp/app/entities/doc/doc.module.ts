import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { DocComponent } from './doc.component';
import { DocDetailComponent } from './doc-detail.component';
import { DocUpdateComponent } from './doc-update.component';
import { DocDeleteDialogComponent } from './doc-delete-dialog.component';
import { docRoute } from './doc.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(docRoute)],
  declarations: [DocComponent, DocDetailComponent, DocUpdateComponent, DocDeleteDialogComponent],
  entryComponents: [DocDeleteDialogComponent],
})
export class MgmtBaseNautiqueDocModule {}
