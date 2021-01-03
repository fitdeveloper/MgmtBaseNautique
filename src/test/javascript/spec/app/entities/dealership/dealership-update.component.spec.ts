import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { DealershipUpdateComponent } from 'app/entities/dealership/dealership-update.component';
import { DealershipService } from 'app/entities/dealership/dealership.service';
import { Dealership } from 'app/shared/model/dealership.model';

describe('Component Tests', () => {
  describe('Dealership Management Update Component', () => {
    let comp: DealershipUpdateComponent;
    let fixture: ComponentFixture<DealershipUpdateComponent>;
    let service: DealershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [DealershipUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DealershipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DealershipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DealershipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dealership(123);
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
        const entity = new Dealership();
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
