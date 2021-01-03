import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICongePolice } from 'app/shared/model/conge-police.model';

@Component({
  selector: 'jhi-conge-police-detail',
  templateUrl: './conge-police-detail.component.html',
})
export class CongePoliceDetailComponent implements OnInit {
  congePolice: ICongePolice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ congePolice }) => (this.congePolice = congePolice));
  }

  previousState(): void {
    window.history.back();
  }
}
