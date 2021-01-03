import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGuarding } from 'app/shared/model/guarding.model';

@Component({
  selector: 'jhi-guarding-detail',
  templateUrl: './guarding-detail.component.html',
})
export class GuardingDetailComponent implements OnInit {
  guarding: IGuarding | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guarding }) => (this.guarding = guarding));
  }

  previousState(): void {
    window.history.back();
  }
}
