import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAssociation, Association } from 'app/shared/model/association.model';
import { AssociationService } from './association.service';
import { AssociationComponent } from './association.component';
import { AssociationDetailComponent } from './association-detail.component';
import { AssociationUpdateComponent } from './association-update.component';

@Injectable({ providedIn: 'root' })
export class AssociationResolve implements Resolve<IAssociation> {
  constructor(private service: AssociationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssociation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((association: HttpResponse<Association>) => {
          if (association.body) {
            return of(association.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Association());
  }
}

export const associationRoute: Routes = [
  {
    path: '',
    component: AssociationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mgmtBaseNautiqueApp.association.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssociationDetailComponent,
    resolve: {
      association: AssociationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.association.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssociationUpdateComponent,
    resolve: {
      association: AssociationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.association.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssociationUpdateComponent,
    resolve: {
      association: AssociationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.association.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
