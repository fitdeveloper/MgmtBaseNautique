import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { MedicalCertificateUpdateComponent } from 'app/entities/medical-certificate/medical-certificate-update.component';
import { MedicalCertificateService } from 'app/entities/medical-certificate/medical-certificate.service';
import { MedicalCertificate } from 'app/shared/model/medical-certificate.model';

describe('Component Tests', () => {
  describe('MedicalCertificate Management Update Component', () => {
    let comp: MedicalCertificateUpdateComponent;
    let fixture: ComponentFixture<MedicalCertificateUpdateComponent>;
    let service: MedicalCertificateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [MedicalCertificateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MedicalCertificateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalCertificateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalCertificateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalCertificate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalCertificate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
