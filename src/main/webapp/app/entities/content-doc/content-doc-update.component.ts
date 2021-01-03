import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IContentDoc, ContentDoc } from 'app/shared/model/content-doc.model';
import { ContentDocService } from './content-doc.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-content-doc-update',
  templateUrl: './content-doc-update.component.html',
})
export class ContentDocUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numberContentDoc: [null, [Validators.required]],
    dataContentDoc: [null, [Validators.required]],
    dataContentDocContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected contentDocService: ContentDocService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentDoc }) => {
      this.updateForm(contentDoc);
      if (!contentDoc.numberContentDoc) {
        const generateUniqueNumber = 'CD_' + Date.now();
        this.editForm.patchValue({
          numberContentDoc: generateUniqueNumber,
        });
      }
    });
  }

  updateForm(contentDoc: IContentDoc): void {
    this.editForm.patchValue({
      id: contentDoc.id,
      numberContentDoc: contentDoc.numberContentDoc,
      dataContentDoc: contentDoc.dataContentDoc,
      dataContentDocContentType: contentDoc.dataContentDocContentType,
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentDoc = this.createFromForm();
    if (contentDoc.id !== undefined) {
      this.subscribeToSaveResponse(this.contentDocService.update(contentDoc));
    } else {
      this.subscribeToSaveResponse(this.contentDocService.create(contentDoc));
    }
  }

  private createFromForm(): IContentDoc {
    return {
      ...new ContentDoc(),
      id: this.editForm.get(['id'])!.value,
      numberContentDoc: this.editForm.get(['numberContentDoc'])!.value,
      dataContentDocContentType: this.editForm.get(['dataContentDocContentType'])!.value,
      dataContentDoc: this.editForm.get(['dataContentDoc'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentDoc>>): void {
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
}
