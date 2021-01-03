import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentDoc } from 'app/shared/model/content-doc.model';
import { ContentDocService } from './content-doc.service';

@Component({
  templateUrl: './content-doc-delete-dialog.component.html',
})
export class ContentDocDeleteDialogComponent {
  contentDoc?: IContentDoc;

  constructor(
    protected contentDocService: ContentDocService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contentDocService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contentDocListModification');
      this.activeModal.close();
    });
  }
}
