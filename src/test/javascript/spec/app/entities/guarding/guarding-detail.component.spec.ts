import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { GuardingDetailComponent } from 'app/entities/guarding/guarding-detail.component';
import { Guarding } from 'app/shared/model/guarding.model';

describe('Component Tests', () => {
  describe('Guarding Management Detail Component', () => {
    let comp: GuardingDetailComponent;
    let fixture: ComponentFixture<GuardingDetailComponent>;
    const route = ({ data: of({ guarding: new Guarding(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [GuardingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GuardingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GuardingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load guarding on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.guarding).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
