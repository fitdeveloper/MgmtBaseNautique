import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalCertificate } from 'app/shared/model/medical-certificate.model';
import { MedicalCertificateService } from './medical-certificate.service';

@Component({
  templateUrl: './medical-certificate-delete-dialog.component.html',
})
export class MedicalCertificateDeleteDialogComponent {
  medicalCertificate?: IMedicalCertificate;

  constructor(
    protected medicalCertificateService: MedicalCertificateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicalCertificateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('medicalCertificateListModification');
      this.activeModal.close();
    });
  }
}
