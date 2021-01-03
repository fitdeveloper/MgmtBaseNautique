import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDealership } from 'app/shared/model/dealership.model';

@Component({
  selector: 'jhi-dealership-detail',
  templateUrl: './dealership-detail.component.html',
})
export class DealershipDetailComponent implements OnInit {
  dealership: IDealership | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealership }) => (this.dealership = dealership));
  }

  previousState(): void {
    window.history.back();
  }
}
