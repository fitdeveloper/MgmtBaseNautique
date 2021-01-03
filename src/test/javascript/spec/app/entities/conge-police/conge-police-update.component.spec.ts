import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { CongePoliceUpdateComponent } from 'app/entities/conge-police/conge-police-update.component';
import { CongePoliceService } from 'app/entities/conge-police/conge-police.service';
import { CongePolice } from 'app/shared/model/conge-police.model';

describe('Component Tests', () => {
  describe('CongePolice Management Update Component', () => {
    let comp: CongePoliceUpdateComponent;
    let fixture: ComponentFixture<CongePoliceUpdateComponent>;
    let service: CongePoliceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [CongePoliceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CongePoliceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CongePoliceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CongePoliceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CongePolice(123);
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
        const entity = new CongePolice();
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
