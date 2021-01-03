import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IInsurance, Insurance } from 'app/shared/model/insurance.model';
import { InsuranceService } from './insurance.service';
import { IDoc } from 'app/shared/model/doc.model';
import { DocService } from 'app/entities/doc/doc.service';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle/vehicle.service';

type SelectableEntity = IDoc | IVehicle;

@Component({
  selector: 'jhi-insurance-update',
  templateUrl: './insurance-update.component.html',
})
export class InsuranceUpdateComponent implements OnInit {
  isSaving = false;
  docs: IDoc[] = [];
  vehicles: IVehicle[] = [];
  endDateInsuranceDp: any;

  editForm = this.fb.group({
    id: [],
    numberInsurance: [null, [Validators.required]],
    endDateInsurance: [null, [Validators.required]],
    validInsurance: [null, [Validators.required]],
    descInsurance: [],
    docId: [],
    vehicleId: [],
  });

  constructor(
    protected insuranceService: InsuranceService,
    protected docService: DocService,
    protected vehicleService: VehicleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ insurance }) => {
      this.updateForm(insurance);
      if (!insurance.numberInsurance) {
        const generateUniqueNumber = 'I_' + Date.now();
        this.editForm.patchValue({
          numberInsurance: generateUniqueNumber,
        });
      }
      this.docService
        .query({ filter: 'insurance-is-null' })
        .pipe(
          map((res: HttpResponse<IDoc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoc[]) => {
          if (!insurance.docId) {
            this.docs = resBody;
          } else {
            this.docService
              .find(insurance.docId)
              .pipe(
                map((subRes: HttpResponse<IDoc>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDoc[]) => (this.docs = concatRes));
          }
        });

      this.vehicleService.query().subscribe((res: HttpResponse<IVehicle[]>) => (this.vehicles = res.body || []));
    });
  }

  updateForm(insurance: IInsurance): void {
    this.editForm.patchValue({
      id: insurance.id,
      numberInsurance: insurance.numberInsurance,
      endDateInsurance: insurance.endDateInsurance,
      validInsurance: insurance.validInsurance,
      descInsurance: insurance.descInsurance,
      docId: insurance.docId,
      vehicleId: insurance.vehicleId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const insurance = this.createFromForm();
    if (insurance.id !== undefined) {
      this.subscribeToSaveResponse(this.insuranceService.update(insurance));
    } else {
      this.subscribeToSaveResponse(this.insuranceService.create(insurance));
    }
  }

  private createFromForm(): IInsurance {
    return {
      ...new Insurance(),
      id: this.editForm.get(['id'])!.value,
      numberInsurance: this.editForm.get(['numberInsurance'])!.value,
      endDateInsurance: this.editForm.get(['endDateInsurance'])!.value,
      validInsurance: this.editForm.get(['validInsurance'])!.value,
      descInsurance: this.editForm.get(['descInsurance'])!.value,
      docId: this.editForm.get(['docId'])!.value,
      vehicleId: this.editForm.get(['vehicleId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsurance>>): void {
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
