import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'member',
        loadChildren: () => import('./member/member.module').then(m => m.MgmtBaseNautiqueMemberModule),
      },
      {
        path: 'association',
        loadChildren: () => import('./association/association.module').then(m => m.MgmtBaseNautiqueAssociationModule),
      },
      {
        path: 'membership',
        loadChildren: () => import('./membership/membership.module').then(m => m.MgmtBaseNautiqueMembershipModule),
      },
      {
        path: 'guarding',
        loadChildren: () => import('./guarding/guarding.module').then(m => m.MgmtBaseNautiqueGuardingModule),
      },
      {
        path: 'vehicle',
        loadChildren: () => import('./vehicle/vehicle.module').then(m => m.MgmtBaseNautiqueVehicleModule),
      },
      {
        path: 'conge-police',
        loadChildren: () => import('./conge-police/conge-police.module').then(m => m.MgmtBaseNautiqueCongePoliceModule),
      },
      {
        path: 'insurance',
        loadChildren: () => import('./insurance/insurance.module').then(m => m.MgmtBaseNautiqueInsuranceModule),
      },
      {
        path: 'dealership',
        loadChildren: () => import('./dealership/dealership.module').then(m => m.MgmtBaseNautiqueDealershipModule),
      },
      {
        path: 'medical-certificate',
        loadChildren: () =>
          import('./medical-certificate/medical-certificate.module').then(m => m.MgmtBaseNautiqueMedicalCertificateModule),
      },
      {
        path: 'doc',
        loadChildren: () => import('./doc/doc.module').then(m => m.MgmtBaseNautiqueDocModule),
      },
      {
        path: 'type-doc',
        loadChildren: () => import('./type-doc/type-doc.module').then(m => m.MgmtBaseNautiqueTypeDocModule),
      },
      {
        path: 'content-doc',
        loadChildren: () => import('./content-doc/content-doc.module').then(m => m.MgmtBaseNautiqueContentDocModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MgmtBaseNautiqueEntityModule {}
