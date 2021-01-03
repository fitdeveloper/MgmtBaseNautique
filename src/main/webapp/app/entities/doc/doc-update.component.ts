import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDoc, Doc } from 'app/shared/model/doc.model';
import { DocService } from './doc.service';
import { IContentDoc } from 'app/shared/model/content-doc.model';
import { ContentDocService } from 'app/entities/content-doc/content-doc.service';
import { ITypeDoc } from 'app/shared/model/type-doc.model';
import { TypeDocService } from 'app/entities/type-doc/type-doc.service';
import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from 'app/entities/membership/membership.service';

type SelectableEntity = IContentDoc | ITypeDoc | IMembership;

@Component({
  selector: 'jhi-doc-update',
  templateUrl: './doc-update.component.html',
})
export class DocUpdateComponent implements OnInit {
  isSaving = false;
  contentdocs: IContentDoc[] = [];
  typedocs: ITypeDoc[] = [];
  memberships: IMembership[] = [];

  editForm = this.fb.group({
    id: [],
    numberDoc: [null, [Validators.required]],
    titleDoc: [null, [Validators.required]],
    sizeDoc: [null, [Validators.required]],
    mimeTypeDoc: [],
    descDoc: [],
    contentDocId: [null, Validators.required],
    typeDocId: [null, Validators.required],
    membershipId: [],
  });

  constructor(
    protected docService: DocService,
    protected contentDocService: ContentDocService,
    protected typeDocService: TypeDocService,
    protected membershipService: MembershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doc }) => {
      this.updateForm(doc);
      if (JSON.stringify(doc) === '{}') {
        const generateUniqueNumber = 'CP_' + Date.now();
        this.editForm.patchValue({
          numberDoc: generateUniqueNumber,
        });
      }
      this.contentDocService
        .query({ filter: 'doc-is-null' })
        .pipe(
          map((res: HttpResponse<IContentDoc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IContentDoc[]) => {
          if (!doc.contentDocId) {
            this.contentdocs = resBody;
          } else {
            this.contentDocService
              .find(doc.contentDocId)
              .pipe(
                map((subRes: HttpResponse<IContentDoc>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IContentDoc[]) => (this.contentdocs = concatRes));
          }
        });

      this.typeDocService
        .query({ filter: 'doc-is-null' })
        .pipe(
          map((res: HttpResponse<ITypeDoc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITypeDoc[]) => {
          if (!doc.typeDocId) {
            this.typedocs = resBody;
          } else {
            this.typeDocService
              .find(doc.typeDocId)
              .pipe(
                map((subRes: HttpResponse<ITypeDoc>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITypeDoc[]) => (this.typedocs = concatRes));
          }
        });

      this.membershipService.query().subscribe((res: HttpResponse<IMembership[]>) => (this.memberships = res.body || []));
    });
  }

  updateForm(doc: IDoc): void {
    this.editForm.patchValue({
      id: doc.id,
      numberDoc: doc.numberDoc,
      titleDoc: doc.titleDoc,
      sizeDoc: doc.sizeDoc,
      mimeTypeDoc: doc.mimeTypeDoc,
      descDoc: doc.descDoc,
      contentDocId: doc.contentDocId,
      typeDocId: doc.typeDocId,
      membershipId: doc.membershipId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const doc = this.createFromForm();
    if (doc.id !== undefined) {
      this.subscribeToSaveResponse(this.docService.update(doc));
    } else {
      this.subscribeToSaveResponse(this.docService.create(doc));
    }
  }

  private createFromForm(): IDoc {
    return {
      ...new Doc(),
      id: this.editForm.get(['id'])!.value,
      numberDoc: this.editForm.get(['numberDoc'])!.value,
      titleDoc: this.editForm.get(['titleDoc'])!.value,
      sizeDoc: this.editForm.get(['sizeDoc'])!.value,
      mimeTypeDoc: this.editForm.get(['mimeTypeDoc'])!.value,
      descDoc: this.editForm.get(['descDoc'])!.value,
      contentDocId: this.editForm.get(['contentDocId'])!.value,
      typeDocId: this.editForm.get(['typeDocId'])!.value,
      membershipId: this.editForm.get(['membershipId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoc>>): void {
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
