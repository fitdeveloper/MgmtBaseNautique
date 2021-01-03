import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';
import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from 'app/entities/membership/membership.service';

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html',
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;
  memberships: IMembership[] = [];

  editForm = this.fb.group({
    id: [],
    numberVehicle: [null, [Validators.required]],
    titleVehicle: [],
    descVehicle: [],
    membershipId: [],
  });

  constructor(
    protected vehicleService: VehicleService,
    protected membershipService: MembershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);
      if (JSON.stringify(vehicle) === '{}') {
        const generateUniqueNumber = 'M_' + Date.now();
        this.editForm.patchValue({
          numberVehicle: generateUniqueNumber,
        });
      }
      this.membershipService.query().subscribe((res: HttpResponse<IMembership[]>) => (this.memberships = res.body || []));
    });
  }

  updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      numberVehicle: vehicle.numberVehicle,
      titleVehicle: vehicle.titleVehicle,
      descVehicle: vehicle.descVehicle,
      membershipId: vehicle.membershipId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  private createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      numberVehicle: this.editForm.get(['numberVehicle'])!.value,
      titleVehicle: this.editForm.get(['titleVehicle'])!.value,
      descVehicle: this.editForm.get(['descVehicle'])!.value,
      membershipId: this.editForm.get(['membershipId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
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

  trackById(index: number, item: IMembership): any {
    return item.id;
  }
}
