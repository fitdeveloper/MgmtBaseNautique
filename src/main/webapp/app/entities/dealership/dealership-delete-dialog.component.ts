import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDealership } from 'app/shared/model/dealership.model';
import { DealershipService } from './dealership.service';

@Component({
  templateUrl: './dealership-delete-dialog.component.html',
})
export class DealershipDeleteDialogComponent {
  dealership?: IDealership;

  constructor(
    protected dealershipService: DealershipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dealershipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dealershipListModification');
      this.activeModal.close();
    });
  }
}
