import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeDoc } from 'app/shared/model/type-doc.model';

@Component({
  selector: 'jhi-type-doc-detail',
  templateUrl: './type-doc-detail.component.html',
})
export class TypeDocDetailComponent implements OnInit {
  typeDoc: ITypeDoc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeDoc }) => (this.typeDoc = typeDoc));
  }

  previousState(): void {
    window.history.back();
  }
}
