import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { ContentDocComponent } from './content-doc.component';
import { ContentDocDetailComponent } from './content-doc-detail.component';
import { ContentDocUpdateComponent } from './content-doc-update.component';
import { ContentDocDeleteDialogComponent } from './content-doc-delete-dialog.component';
import { contentDocRoute } from './content-doc.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(contentDocRoute)],
  declarations: [ContentDocComponent, ContentDocDetailComponent, ContentDocUpdateComponent, ContentDocDeleteDialogComponent],
  entryComponents: [ContentDocDeleteDialogComponent],
})
export class MgmtBaseNautiqueContentDocModule {}
