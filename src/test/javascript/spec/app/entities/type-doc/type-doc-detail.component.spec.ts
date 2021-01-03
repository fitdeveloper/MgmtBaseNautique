import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { TypeDocDetailComponent } from 'app/entities/type-doc/type-doc-detail.component';
import { TypeDoc } from 'app/shared/model/type-doc.model';

describe('Component Tests', () => {
  describe('TypeDoc Management Detail Component', () => {
    let comp: TypeDocDetailComponent;
    let fixture: ComponentFixture<TypeDocDetailComponent>;
    const route = ({ data: of({ typeDoc: new TypeDoc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [TypeDocDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeDocDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeDocDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeDoc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeDoc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
