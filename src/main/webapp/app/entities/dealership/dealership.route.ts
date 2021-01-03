import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDealership, Dealership } from 'app/shared/model/dealership.model';
import { DealershipService } from './dealership.service';
import { DealershipComponent } from './dealership.component';
import { DealershipDetailComponent } from './dealership-detail.component';
import { DealershipUpdateComponent } from './dealership-update.component';

@Injectable({ providedIn: 'root' })
export class DealershipResolve implements Resolve<IDealership> {
  constructor(private service: DealershipService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDealership> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dealership: HttpResponse<Dealership>) => {
          if (dealership.body) {
            return of(dealership.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dealership());
  }
}

export const dealershipRoute: Routes = [
  {
    path: '',
    component: DealershipComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mgmtBaseNautiqueApp.dealership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DealershipDetailComponent,
    resolve: {
      dealership: DealershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.dealership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DealershipUpdateComponent,
    resolve: {
      dealership: DealershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.dealership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DealershipUpdateComponent,
    resolve: {
      dealership: DealershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.dealership.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
