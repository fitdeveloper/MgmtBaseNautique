import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeDoc, TypeDoc } from 'app/shared/model/type-doc.model';
import { TypeDocService } from './type-doc.service';
import { TypeDocComponent } from './type-doc.component';
import { TypeDocDetailComponent } from './type-doc-detail.component';
import { TypeDocUpdateComponent } from './type-doc-update.component';

@Injectable({ providedIn: 'root' })
export class TypeDocResolve implements Resolve<ITypeDoc> {
  constructor(private service: TypeDocService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeDoc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeDoc: HttpResponse<TypeDoc>) => {
          if (typeDoc.body) {
            return of(typeDoc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeDoc());
  }
}

export const typeDocRoute: Routes = [
  {
    path: '',
    component: TypeDocComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mgmtBaseNautiqueApp.typeDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeDocDetailComponent,
    resolve: {
      typeDoc: TypeDocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.typeDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeDocUpdateComponent,
    resolve: {
      typeDoc: TypeDocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.typeDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeDocUpdateComponent,
    resolve: {
      typeDoc: TypeDocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.typeDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
