import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { MedicalCertificateDetailComponent } from 'app/entities/medical-certificate/medical-certificate-detail.component';
import { MedicalCertificate } from 'app/shared/model/medical-certificate.model';

describe('Component Tests', () => {
  describe('MedicalCertificate Management Detail Component', () => {
    let comp: MedicalCertificateDetailComponent;
    let fixture: ComponentFixture<MedicalCertificateDetailComponent>;
    const route = ({ data: of({ medicalCertificate: new MedicalCertificate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [MedicalCertificateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MedicalCertificateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalCertificateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load medicalCertificate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalCertificate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
