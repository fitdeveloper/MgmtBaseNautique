import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { ContentDocUpdateComponent } from 'app/entities/content-doc/content-doc-update.component';
import { ContentDocService } from 'app/entities/content-doc/content-doc.service';
import { ContentDoc } from 'app/shared/model/content-doc.model';

describe('Component Tests', () => {
  describe('ContentDoc Management Update Component', () => {
    let comp: ContentDocUpdateComponent;
    let fixture: ComponentFixture<ContentDocUpdateComponent>;
    let service: ContentDocService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [ContentDocUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContentDocUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContentDocUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentDocService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContentDoc(123);
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
        const entity = new ContentDoc();
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
