import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICongePolice } from 'app/shared/model/conge-police.model';
import { CongePoliceService } from './conge-police.service';

@Component({
  templateUrl: './conge-police-delete-dialog.component.html',
})
export class CongePoliceDeleteDialogComponent {
  congePolice?: ICongePolice;

  constructor(
    protected congePoliceService: CongePoliceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.congePoliceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('congePoliceListModification');
      this.activeModal.close();
    });
  }
}
