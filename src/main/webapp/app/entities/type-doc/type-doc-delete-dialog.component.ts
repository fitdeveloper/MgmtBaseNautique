import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeDoc } from 'app/shared/model/type-doc.model';
import { TypeDocService } from './type-doc.service';

@Component({
  templateUrl: './type-doc-delete-dialog.component.html',
})
export class TypeDocDeleteDialogComponent {
  typeDoc?: ITypeDoc;

  constructor(protected typeDocService: TypeDocService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeDocService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeDocListModification');
      this.activeModal.close();
    });
  }
}
