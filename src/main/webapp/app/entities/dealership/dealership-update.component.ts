import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDealership, Dealership } from 'app/shared/model/dealership.model';
import { DealershipService } from './dealership.service';
import { IDoc } from 'app/shared/model/doc.model';
import { DocService } from 'app/entities/doc/doc.service';
import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from 'app/entities/membership/membership.service';

type SelectableEntity = IDoc | IMembership;

@Component({
  selector: 'jhi-dealership-update',
  templateUrl: './dealership-update.component.html',
})
export class DealershipUpdateComponent implements OnInit {
  isSaving = false;
  docs: IDoc[] = [];
  memberships: IMembership[] = [];
  endDateDealershipDp: any;

  editForm = this.fb.group({
    id: [],
    numberDealership: [null, [Validators.required]],
    endDateDealership: [null, [Validators.required]],
    validDealership: [null, [Validators.required]],
    descDealership: [],
    docId: [],
    membershipId: [],
  });

  constructor(
    protected dealershipService: DealershipService,
    protected docService: DocService,
    protected membershipService: MembershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealership }) => {
      this.updateForm(dealership);
      if (!dealership.numberDealership) {
        const generateUniqueNumber = 'DS_' + Date.now();
        this.editForm.patchValue({
          numberDealership: generateUniqueNumber,
        });
      }
      this.docService
        .query({ filter: 'dealership-is-null' })
        .pipe(
          map((res: HttpResponse<IDoc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoc[]) => {
          if (!dealership.docId) {
            this.docs = resBody;
          } else {
            this.docService
              .find(dealership.docId)
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

  updateForm(dealership: IDealership): void {
    this.editForm.patchValue({
      id: dealership.id,
      numberDealership: dealership.numberDealership,
      endDateDealership: dealership.endDateDealership,
      validDealership: dealership.validDealership,
      descDealership: dealership.descDealership,
      docId: dealership.docId,
      membershipId: dealership.membershipId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dealership = this.createFromForm();
    if (dealership.id !== undefined) {
      this.subscribeToSaveResponse(this.dealershipService.update(dealership));
    } else {
      this.subscribeToSaveResponse(this.dealershipService.create(dealership));
    }
  }

  private createFromForm(): IDealership {
    return {
      ...new Dealership(),
      id: this.editForm.get(['id'])!.value,
      numberDealership: this.editForm.get(['numberDealership'])!.value,
      endDateDealership: this.editForm.get(['endDateDealership'])!.value,
      validDealership: this.editForm.get(['validDealership'])!.value,
      descDealership: this.editForm.get(['descDealership'])!.value,
      docId: this.editForm.get(['docId'])!.value,
      membershipId: this.editForm.get(['membershipId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDealership>>): void {
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
