import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInsurance } from 'app/shared/model/insurance.model';

@Component({
  selector: 'jhi-insurance-detail',
  templateUrl: './insurance-detail.component.html',
})
export class InsuranceDetailComponent implements OnInit {
  insurance: IInsurance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ insurance }) => (this.insurance = insurance));
  }

  previousState(): void {
    window.history.back();
  }
}
