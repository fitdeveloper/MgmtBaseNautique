import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssociation } from 'app/shared/model/association.model';
import { AssociationService } from './association.service';

@Component({
  templateUrl: './association-delete-dialog.component.html',
})
export class AssociationDeleteDialogComponent {
  association?: IAssociation;

  constructor(
    protected associationService: AssociationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.associationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('associationListModification');
      this.activeModal.close();
    });
  }
}
