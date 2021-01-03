import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAssociation, Association } from 'app/shared/model/association.model';
import { AssociationService } from './association.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-association-update',
  templateUrl: './association-update.component.html',
})
export class AssociationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numberAssociation: [null, [Validators.required]],
    nameAssociation: [null, [Validators.required]],
    imageAssociation: [],
    imageAssociationContentType: [],
    descAssociation: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected associationService: AssociationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ association }) => {
      this.updateForm(association);
      if (JSON.stringify(association) === '{}') {
        const generateUniqueNumber = 'A_' + Date.now();
        this.editForm.patchValue({
          numberAssociation: generateUniqueNumber,
        });
      }
    });
  }

  updateForm(association: IAssociation): void {
    this.editForm.patchValue({
      id: association.id,
      numberAssociation: association.numberAssociation,
      nameAssociation: association.nameAssociation,
      imageAssociation: association.imageAssociation,
      imageAssociationContentType: association.imageAssociationContentType,
      descAssociation: association.descAssociation,
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
    const association = this.createFromForm();
    if (association.id !== undefined) {
      this.subscribeToSaveResponse(this.associationService.update(association));
    } else {
      this.subscribeToSaveResponse(this.associationService.create(association));
    }
  }

  private createFromForm(): IAssociation {
    return {
      ...new Association(),
      id: this.editForm.get(['id'])!.value,
      numberAssociation: this.editForm.get(['numberAssociation'])!.value,
      nameAssociation: this.editForm.get(['nameAssociation'])!.value,
      imageAssociationContentType: this.editForm.get(['imageAssociationContentType'])!.value,
      imageAssociation: this.editForm.get(['imageAssociation'])!.value,
      descAssociation: this.editForm.get(['descAssociation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssociation>>): void {
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
