import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContentDoc, ContentDoc } from 'app/shared/model/content-doc.model';
import { ContentDocService } from './content-doc.service';
import { ContentDocComponent } from './content-doc.component';
import { ContentDocDetailComponent } from './content-doc-detail.component';
import { ContentDocUpdateComponent } from './content-doc-update.component';

@Injectable({ providedIn: 'root' })
export class ContentDocResolve implements Resolve<IContentDoc> {
  constructor(private service: ContentDocService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContentDoc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contentDoc: HttpResponse<ContentDoc>) => {
          if (contentDoc.body) {
            return of(contentDoc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContentDoc());
  }
}

export const contentDocRoute: Routes = [
  {
    path: '',
    component: ContentDocComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mgmtBaseNautiqueApp.contentDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContentDocDetailComponent,
    resolve: {
      contentDoc: ContentDocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.contentDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContentDocUpdateComponent,
    resolve: {
      contentDoc: ContentDocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.contentDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContentDocUpdateComponent,
    resolve: {
      contentDoc: ContentDocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.contentDoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
