import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMember, Member } from 'app/shared/model/member.model';
import { MemberService } from './member.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAssociation } from 'app/shared/model/association.model';
import { AssociationService } from 'app/entities/association/association.service';

type SelectableEntity = IMember | IAssociation;

@Component({
  selector: 'jhi-member-update',
  templateUrl: './member-update.component.html',
})
export class MemberUpdateComponent implements OnInit {
  isSaving = false;
  parents: IMember[] = [];
  associations: IAssociation[] = [];
  dobMemberDp: any;

  editForm = this.fb.group({
    id: [],
    numberMember: [null, [Validators.required]],
    typeMember: [null, [Validators.required]],
    cinMember: [null, [Validators.required]],
    firstnameMember: [],
    lastnameMember: [],
    emailMember: [null, [Validators.required, Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$')]],
    numberPhoneMember: [],
    dobMember: [null, [Validators.required]],
    adressMember: [],
    imageMember: [],
    imageMemberContentType: [],
    descMember: [],
    parentId: [],
    associationId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected memberService: MemberService,
    protected associationService: AssociationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ member }) => {
      this.updateForm(member);

      this.memberService
        .query({ filter: 'child-is-null' })
        .pipe(
          map((res: HttpResponse<IMember[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMember[]) => {
          if (!member.parentId) {
            this.parents = resBody;
          } else {
            this.memberService
              .find(member.parentId)
              .pipe(
                map((subRes: HttpResponse<IMember>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMember[]) => (this.parents = concatRes));
          }
        });

      this.associationService.query().subscribe((res: HttpResponse<IAssociation[]>) => (this.associations = res.body || []));
    });
  }

  updateForm(member: IMember): void {
    this.editForm.patchValue({
      id: member.id,
      numberMember: member.numberMember,
      typeMember: member.typeMember,
      cinMember: member.cinMember,
      firstnameMember: member.firstnameMember,
      lastnameMember: member.lastnameMember,
      emailMember: member.emailMember,
      numberPhoneMember: member.numberPhoneMember,
      dobMember: member.dobMember,
      adressMember: member.adressMember,
      imageMember: member.imageMember,
      imageMemberContentType: member.imageMemberContentType,
      descMember: member.descMember,
      parentId: member.parentId,
      associationId: member.associationId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('mgmtBaseNautiqueApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const member = this.createFromForm();
    if (member.id !== undefined) {
      this.subscribeToSaveResponse(this.memberService.update(member));
    } else {
      this.subscribeToSaveResponse(this.memberService.create(member));
    }
  }

  private createFromForm(): IMember {
    return {
      ...new Member(),
      id: this.editForm.get(['id'])!.value,
      numberMember: this.editForm.get(['numberMember'])!.value,
      typeMember: this.editForm.get(['typeMember'])!.value,
      cinMember: this.editForm.get(['cinMember'])!.value,
      firstnameMember: this.editForm.get(['firstnameMember'])!.value,
      lastnameMember: this.editForm.get(['lastnameMember'])!.value,
      emailMember: this.editForm.get(['emailMember'])!.value,
      numberPhoneMember: this.editForm.get(['numberPhoneMember'])!.value,
      dobMember: this.editForm.get(['dobMember'])!.value,
      adressMember: this.editForm.get(['adressMember'])!.value,
      imageMemberContentType: this.editForm.get(['imageMemberContentType'])!.value,
      imageMember: this.editForm.get(['imageMember'])!.value,
      descMember: this.editForm.get(['descMember'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
      associationId: this.editForm.get(['associationId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMember>>): void {
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
