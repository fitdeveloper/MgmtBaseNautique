import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMedicalCertificate, MedicalCertificate } from 'app/shared/model/medical-certificate.model';
import { MedicalCertificateService } from './medical-certificate.service';
import { IDoc } from 'app/shared/model/doc.model';
import { DocService } from 'app/entities/doc/doc.service';
import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from 'app/entities/membership/membership.service';

type SelectableEntity = IDoc | IMembership;

@Component({
  selector: 'jhi-medical-certificate-update',
  templateUrl: './medical-certificate-update.component.html',
})
export class MedicalCertificateUpdateComponent implements OnInit {
  isSaving = false;
  docs: IDoc[] = [];
  memberships: IMembership[] = [];
  endDateMedicalCertificateDp: any;

  editForm = this.fb.group({
    id: [],
    numberMedicalCertificate: [null, [Validators.required]],
    endDateMedicalCertificate: [null, [Validators.required]],
    validMedicalCertificate: [null, [Validators.required]],
    descMedicalCertificate: [],
    docId: [],
    membershipId: [],
  });

  constructor(
    protected medicalCertificateService: MedicalCertificateService,
    protected docService: DocService,
    protected membershipService: MembershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCertificate }) => {
      this.updateForm(medicalCertificate);

      this.docService
        .query({ filter: 'medicalcertificate-is-null' })
        .pipe(
          map((res: HttpResponse<IDoc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoc[]) => {
          if (!medicalCertificate.docId) {
            this.docs = resBody;
          } else {
            this.docService
              .find(medicalCertificate.docId)
              .pipe(
                map((subRes: HttpResponse<IDoc>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDoc[]) => (this.docs = concatRes));
          }
        });

      this.membershipService.query().subscribe((res: HttpResponse<IMembership[]>) => (this.memberships = res.body || []));
    });
  }

  updateForm(medicalCertificate: IMedicalCertificate): void {
    this.editForm.patchValue({
      id: medicalCertificate.id,
      numberMedicalCertificate: medicalCertificate.numberMedicalCertificate,
      endDateMedicalCertificate: medicalCertificate.endDateMedicalCertificate,
      validMedicalCertificate: medicalCertificate.validMedicalCertificate,
      descMedicalCertificate: medicalCertificate.descMedicalCertificate,
      docId: medicalCertificate.docId,
      membershipId: medicalCertificate.membershipId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalCertificate = this.createFromForm();
    if (medicalCertificate.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalCertificateService.update(medicalCertificate));
    } else {
      this.subscribeToSaveResponse(this.medicalCertificateService.create(medicalCertificate));
    }
  }

  private createFromForm(): IMedicalCertificate {
    return {
      ...new MedicalCertificate(),
      id: this.editForm.get(['id'])!.value,
      numberMedicalCertificate: this.editForm.get(['numberMedicalCertificate'])!.value,
      endDateMedicalCertificate: this.editForm.get(['endDateMedicalCertificate'])!.value,
      validMedicalCertificate: this.editForm.get(['validMedicalCertificate'])!.value,
      descMedicalCertificate: this.editForm.get(['descMedicalCertificate'])!.value,
      docId: this.editForm.get(['docId'])!.value,
      membershipId: this.editForm.get(['membershipId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalCertificate>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
