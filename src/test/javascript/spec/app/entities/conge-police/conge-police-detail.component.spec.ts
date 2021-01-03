import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { CongePoliceDetailComponent } from 'app/entities/conge-police/conge-police-detail.component';
import { CongePolice } from 'app/shared/model/conge-police.model';

describe('Component Tests', () => {
  describe('CongePolice Management Detail Component', () => {
    let comp: CongePoliceDetailComponent;
    let fixture: ComponentFixture<CongePoliceDetailComponent>;
    const route = ({ data: of({ congePolice: new CongePolice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [CongePoliceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CongePoliceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CongePoliceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load congePolice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.congePolice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
