import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { DealershipDetailComponent } from 'app/entities/dealership/dealership-detail.component';
import { Dealership } from 'app/shared/model/dealership.model';

describe('Component Tests', () => {
  describe('Dealership Management Detail Component', () => {
    let comp: DealershipDetailComponent;
    let fixture: ComponentFixture<DealershipDetailComponent>;
    const route = ({ data: of({ dealership: new Dealership(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [DealershipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DealershipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DealershipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dealership on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dealership).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
