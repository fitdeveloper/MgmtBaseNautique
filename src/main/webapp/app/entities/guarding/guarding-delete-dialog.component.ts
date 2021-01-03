import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGuarding } from 'app/shared/model/guarding.model';
import { GuardingService } from './guarding.service';

@Component({
  templateUrl: './guarding-delete-dialog.component.html',
})
export class GuardingDeleteDialogComponent {
  guarding?: IGuarding;

  constructor(protected guardingService: GuardingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.guardingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('guardingListModification');
      this.activeModal.close();
    });
  }
}
