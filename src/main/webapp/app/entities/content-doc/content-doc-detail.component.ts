import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IContentDoc } from 'app/shared/model/content-doc.model';

@Component({
  selector: 'jhi-content-doc-detail',
  templateUrl: './content-doc-detail.component.html',
})
export class ContentDocDetailComponent implements OnInit {
  contentDoc: IContentDoc | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentDoc }) => (this.contentDoc = contentDoc));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
