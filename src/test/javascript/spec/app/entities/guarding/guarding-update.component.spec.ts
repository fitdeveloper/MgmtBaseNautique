import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { GuardingUpdateComponent } from 'app/entities/guarding/guarding-update.component';
import { GuardingService } from 'app/entities/guarding/guarding.service';
import { Guarding } from 'app/shared/model/guarding.model';

describe('Component Tests', () => {
  describe('Guarding Management Update Component', () => {
    let comp: GuardingUpdateComponent;
    let fixture: ComponentFixture<GuardingUpdateComponent>;
    let service: GuardingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [GuardingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GuardingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GuardingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GuardingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Guarding(123);
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
        const entity = new Guarding();
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
