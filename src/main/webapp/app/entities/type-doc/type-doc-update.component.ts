import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeDoc, TypeDoc } from 'app/shared/model/type-doc.model';
import { TypeDocService } from './type-doc.service';

@Component({
  selector: 'jhi-type-doc-update',
  templateUrl: './type-doc-update.component.html',
})
export class TypeDocUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numberTypeDoc: [null, [Validators.required]],
    titleTypeDoc: [null, [Validators.required]],
    descTypeDoc: [],
  });

  constructor(protected typeDocService: TypeDocService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeDoc }) => {
      this.updateForm(typeDoc);
      if (JSON.stringify(typeDoc) === '{}') {
        const generateUniqueNumber = 'M_' + Date.now();
        this.editForm.patchValue({
          numberTypeDoc: generateUniqueNumber,
        });
      }
    });
  }

  updateForm(typeDoc: ITypeDoc): void {
    this.editForm.patchValue({
      id: typeDoc.id,
      numberTypeDoc: typeDoc.numberTypeDoc,
      titleTypeDoc: typeDoc.titleTypeDoc,
      descTypeDoc: typeDoc.descTypeDoc,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeDoc = this.createFromForm();
    if (typeDoc.id !== undefined) {
      this.subscribeToSaveResponse(this.typeDocService.update(typeDoc));
    } else {
      this.subscribeToSaveResponse(this.typeDocService.create(typeDoc));
    }
  }

  private createFromForm(): ITypeDoc {
    return {
      ...new TypeDoc(),
      id: this.editForm.get(['id'])!.value,
      numberTypeDoc: this.editForm.get(['numberTypeDoc'])!.value,
      titleTypeDoc: this.editForm.get(['titleTypeDoc'])!.value,
      descTypeDoc: this.editForm.get(['descTypeDoc'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeDoc>>): void {
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
