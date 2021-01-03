import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICongePolice, CongePolice } from 'app/shared/model/conge-police.model';
import { CongePoliceService } from './conge-police.service';
import { IDoc } from 'app/shared/model/doc.model';
import { DocService } from 'app/entities/doc/doc.service';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle/vehicle.service';

type SelectableEntity = IDoc | IVehicle;

@Component({
  selector: 'jhi-conge-police-update',
  templateUrl: './conge-police-update.component.html',
})
export class CongePoliceUpdateComponent implements OnInit {
  isSaving = false;
  docs: IDoc[] = [];
  vehicles: IVehicle[] = [];
  endDateCongePoliceDp: any;

  editForm = this.fb.group({
    id: [],
    numberCongePolice: [null, [Validators.required]],
    endDateCongePolice: [null, [Validators.required]],
    validCongePolice: [null, [Validators.required]],
    descCongePolice: [],
    docId: [],
    vehicleId: [],
  });

  constructor(
    protected congePoliceService: CongePoliceService,
    protected docService: DocService,
    protected vehicleService: VehicleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ congePolice }) => {
      this.updateForm(congePolice);
      if (!congePolice.numberCongePolice) {
        const generateUniqueNumber = 'CP_' + Date.now();
        this.editForm.patchValue({
          numberCongePolice: generateUniqueNumber,
        });
      }
      this.docService
        .query({ filter: 'congepolice-is-null' })
        .pipe(
          map((res: HttpResponse<IDoc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoc[]) => {
          if (!congePolice.docId) {
            this.docs = resBody;
          } else {
            this.docService
              .find(congePolice.docId)
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

  updateForm(congePolice: ICongePolice): void {
    this.editForm.patchValue({
      id: congePolice.id,
      numberCongePolice: congePolice.numberCongePolice,
      endDateCongePolice: congePolice.endDateCongePolice,
      validCongePolice: congePolice.validCongePolice,
      descCongePolice: congePolice.descCongePolice,
      docId: congePolice.docId,
      vehicleId: congePolice.vehicleId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const congePolice = this.createFromForm();
    if (congePolice.id !== undefined) {
      this.subscribeToSaveResponse(this.congePoliceService.update(congePolice));
    } else {
      this.subscribeToSaveResponse(this.congePoliceService.create(congePolice));
    }
  }

  private createFromForm(): ICongePolice {
    return {
      ...new CongePolice(),
      id: this.editForm.get(['id'])!.value,
      numberCongePolice: this.editForm.get(['numberCongePolice'])!.value,
      endDateCongePolice: this.editForm.get(['endDateCongePolice'])!.value,
      validCongePolice: this.editForm.get(['validCongePolice'])!.value,
      descCongePolice: this.editForm.get(['descCongePolice'])!.value,
      docId: this.editForm.get(['docId'])!.value,
      vehicleId: this.editForm.get(['vehicleId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICongePolice>>): void {
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
