import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MgmtBaseNautiqueSharedModule } from 'app/shared/shared.module';
import { MedicalCertificateComponent } from './medical-certificate.component';
import { MedicalCertificateDetailComponent } from './medical-certificate-detail.component';
import { MedicalCertificateUpdateComponent } from './medical-certificate-update.component';
import { MedicalCertificateDeleteDialogComponent } from './medical-certificate-delete-dialog.component';
import { medicalCertificateRoute } from './medical-certificate.route';

@NgModule({
  imports: [MgmtBaseNautiqueSharedModule, RouterModule.forChild(medicalCertificateRoute)],
  declarations: [
    MedicalCertificateComponent,
    MedicalCertificateDetailComponent,
    MedicalCertificateUpdateComponent,
    MedicalCertificateDeleteDialogComponent,
  ],
  entryComponents: [MedicalCertificateDeleteDialogComponent],
})
export class MgmtBaseNautiqueMedicalCertificateModule {}
