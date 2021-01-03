import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMedicalCertificate, MedicalCertificate } from 'app/shared/model/medical-certificate.model';
import { MedicalCertificateService } from './medical-certificate.service';
import { MedicalCertificateComponent } from './medical-certificate.component';
import { MedicalCertificateDetailComponent } from './medical-certificate-detail.component';
import { MedicalCertificateUpdateComponent } from './medical-certificate-update.component';

@Injectable({ providedIn: 'root' })
export class MedicalCertificateResolve implements Resolve<IMedicalCertificate> {
  constructor(private service: MedicalCertificateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalCertificate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((medicalCertificate: HttpResponse<MedicalCertificate>) => {
          if (medicalCertificate.body) {
            return of(medicalCertificate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MedicalCertificate());
  }
}

export const medicalCertificateRoute: Routes = [
  {
    path: '',
    component: MedicalCertificateComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mgmtBaseNautiqueApp.medicalCertificate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MedicalCertificateDetailComponent,
    resolve: {
      medicalCertificate: MedicalCertificateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.medicalCertificate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedicalCertificateUpdateComponent,
    resolve: {
      medicalCertificate: MedicalCertificateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.medicalCertificate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedicalCertificateUpdateComponent,
    resolve: {
      medicalCertificate: MedicalCertificateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mgmtBaseNautiqueApp.medicalCertificate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
