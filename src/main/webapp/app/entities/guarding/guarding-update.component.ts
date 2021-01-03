import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGuarding, Guarding } from 'app/shared/model/guarding.model';
import { GuardingService } from './guarding.service';
import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from 'app/entities/membership/membership.service';

@Component({
  selector: 'jhi-guarding-update',
  templateUrl: './guarding-update.component.html',
})
export class GuardingUpdateComponent implements OnInit {
  isSaving = false;
  memberships: IMembership[] = [];

  editForm = this.fb.group({
    id: [],
    numberGuarding: [null, [Validators.required]],
    titleGuarding: [],
    descGuarding: [],
    membershipId: [],
  });

  constructor(
    protected guardingService: GuardingService,
    protected membershipService: MembershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guarding }) => {
      this.updateForm(guarding);
      if (!guarding.numberGuarding) {
        const generateUniqueNumber = 'G_' + Date.now();
        this.editForm.patchValue({
          numberGuarding: generateUniqueNumber,
        });
      }
      this.membershipService.query().subscribe((res: HttpResponse<IMembership[]>) => (this.memberships = res.body || []));
    });
  }

  updateForm(guarding: IGuarding): void {
    this.editForm.patchValue({
      id: guarding.id,
      numberGuarding: guarding.numberGuarding,
      titleGuarding: guarding.titleGuarding,
      descGuarding: guarding.descGuarding,
      membershipId: guarding.membershipId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guarding = this.createFromForm();
    if (guarding.id !== undefined) {
      this.subscribeToSaveResponse(this.guardingService.update(guarding));
    } else {
      this.subscribeToSaveResponse(this.guardingService.create(guarding));
    }
  }

  private createFromForm(): IGuarding {
    return {
      ...new Guarding(),
      id: this.editForm.get(['id'])!.value,
      numberGuarding: this.editForm.get(['numberGuarding'])!.value,
      titleGuarding: this.editForm.get(['titleGuarding'])!.value,
      descGuarding: this.editForm.get(['descGuarding'])!.value,
      membershipId: this.editForm.get(['membershipId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuarding>>): void {
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
