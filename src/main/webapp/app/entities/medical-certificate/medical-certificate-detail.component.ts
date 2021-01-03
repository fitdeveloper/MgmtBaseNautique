import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalCertificate } from 'app/shared/model/medical-certificate.model';

@Component({
  selector: 'jhi-medical-certificate-detail',
  templateUrl: './medical-certificate-detail.component.html',
})
export class MedicalCertificateDetailComponent implements OnInit {
  medicalCertificate: IMedicalCertificate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCertificate }) => (this.medicalCertificate = medicalCertificate));
  }

  previousState(): void {
    window.history.back();
  }
}
