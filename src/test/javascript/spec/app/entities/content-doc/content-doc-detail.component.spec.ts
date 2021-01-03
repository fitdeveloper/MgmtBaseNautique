import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MgmtBaseNautiqueTestModule } from '../../../test.module';
import { ContentDocDetailComponent } from 'app/entities/content-doc/content-doc-detail.component';
import { ContentDoc } from 'app/shared/model/content-doc.model';

describe('Component Tests', () => {
  describe('ContentDoc Management Detail Component', () => {
    let comp: ContentDocDetailComponent;
    let fixture: ComponentFixture<ContentDocDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ contentDoc: new ContentDoc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MgmtBaseNautiqueTestModule],
        declarations: [ContentDocDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContentDocDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentDocDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load contentDoc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contentDoc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
