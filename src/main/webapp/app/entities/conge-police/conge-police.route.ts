import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICongePolice, CongePolice } from 'app/shared/model/conge-police.model';
import { CongePoliceService } from './conge-police.service';
import { CongePoliceComponent } from './conge-police.component';
import { CongePoliceDetailComponent } from './conge-police-detail.component';
import { CongePoliceUpdateComponent } from './conge-police-update.component';

@Injectable({ providedIn: 'root' })
export class CongePoliceResolve implements Resolve<ICongePolice> {
  constructor(private service: CongePoliceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICongePolice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((congePolice: HttpResponse<CongePolice>) => {
          if (congePolice.body) {
            return of(congePolice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CongePolice());
  }
}

export const congePoliceRoute: Routes = [
  {
    path: '',
    component: CongePoliceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mgmtBaseNautiqueApp.congePolice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CongePoliceDetailComponent,
    resolve: {
      congePolice: CongePoliceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.congePolice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CongePoliceUpdateComponent,
    resolve: {
      congePolice: CongePoliceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.congePolice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CongePoliceUpdateComponent,
    resolve: {
      congePolice: CongePoliceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.congePolice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
