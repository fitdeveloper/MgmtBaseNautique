import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMembership, Membership } from 'app/shared/model/membership.model';
import { MembershipService } from './membership.service';
import { IMember } from 'app/shared/model/member.model';
import { MemberService } from 'app/entities/member/member.service';

@Component({
  selector: 'jhi-membership-update',
  templateUrl: './membership-update.component.html',
})
export class MembershipUpdateComponent implements OnInit {
  isSaving = false;
  members: IMember[] = [];
  startDateMembershipDp: any;
  endDateMembershipDp: any;

  editForm = this.fb.group({
    id: [],
    numberMembership: [null, [Validators.required]],
    amountMembership: [],
    startDateMembership: [null, [Validators.required]],
    endDateMembership: [null, [Validators.required]],
    validMembership: [null, [Validators.required]],
    descMembership: [],
    memberId: [],
  });

  constructor(
    protected membershipService: MembershipService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membership }) => {
      this.updateForm(membership);
      if (!membership.numberMembership) {
        const generateUniqueNumber = 'MS_' + Date.now();
        this.editForm.patchValue({
          numberMembership: generateUniqueNumber,
        });
      }
      this.memberService.query().subscribe((res: HttpResponse<IMember[]>) => (this.members = res.body || []));
    });
  }

  updateForm(membership: IMembership): void {
    this.editForm.patchValue({
      id: membership.id,
      numberMembership: membership.numberMembership,
      amountMembership: membership.amountMembership,
      startDateMembership: membership.startDateMembership,
      endDateMembership: membership.endDateMembership,
      validMembership: membership.validMembership,
      descMembership: membership.descMembership,
      memberId: membership.memberId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membership = this.createFromForm();
    if (membership.id !== undefined) {
      this.subscribeToSaveResponse(this.membershipService.update(membership));
    } else {
      this.subscribeToSaveResponse(this.membershipService.create(membership));
    }
  }

  private createFromForm(): IMembership {
    return {
      ...new Membership(),
      id: this.editForm.get(['id'])!.value,
      numberMembership: this.editForm.get(['numberMembership'])!.value,
      amountMembership: this.editForm.get(['amountMembership'])!.value,
      startDateMembership: this.editForm.get(['startDateMembership'])!.value,
      endDateMembership: this.editForm.get(['endDateMembership'])!.value,
      validMembership: this.editForm.get(['validMembership'])!.value,
      descMembership: this.editForm.get(['descMembership'])!.value,
      memberId: this.editForm.get(['memberId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembership>>): void {
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

  trackById(index: number, item: IMember): any {
    return item.id;
  }
}
