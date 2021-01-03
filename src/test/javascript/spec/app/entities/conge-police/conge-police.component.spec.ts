import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { CongePoliceComponent } from 'app/entities/conge-police/conge-police.component';
import { CongePoliceService } from 'app/entities/conge-police/conge-police.service';
import { CongePolice } from 'app/shared/model/conge-police.model';

describe('Component Tests', () => {
  describe('CongePolice Management Component', () => {
    let comp: CongePoliceComponent;
    let fixture: ComponentFixture<CongePoliceComponent>;
    let service: CongePoliceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [CongePoliceComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(CongePoliceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CongePoliceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CongePoliceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CongePolice(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.congePolices && comp.congePolices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CongePolice(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.congePolices && comp.congePolices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
