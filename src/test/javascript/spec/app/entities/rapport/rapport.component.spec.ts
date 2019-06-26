/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RapportComponent } from 'app/entities/rapport/rapport.component';
import { RapportService } from 'app/entities/rapport/rapport.service';
import { Rapport } from 'app/shared/model/rapport.model';

describe('Component Tests', () => {
  describe('Rapport Management Component', () => {
    let comp: RapportComponent;
    let fixture: ComponentFixture<RapportComponent>;
    let service: RapportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [RapportComponent],
        providers: []
      })
        .overrideTemplate(RapportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RapportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RapportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Rapport(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rapports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
