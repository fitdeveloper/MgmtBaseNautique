import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { TypeDocComponent } from './type-doc.component';
import { TypeDocDetailComponent } from './type-doc-detail.component';
import { TypeDocUpdateComponent } from './type-doc-update.component';
import { TypeDocDeleteDialogComponent } from './type-doc-delete-dialog.component';
import { typeDocRoute } from './type-doc.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(typeDocRoute)],
  declarations: [TypeDocComponent, TypeDocDetailComponent, TypeDocUpdateComponent, TypeDocDeleteDialogComponent],
  entryComponents: [TypeDocDeleteDialogComponent],
})
export class MgmtBaseNautiqueTypeDocModule {}
