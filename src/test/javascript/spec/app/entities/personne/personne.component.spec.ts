/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PersonneComponent } from 'app/entities/personne/personne.component';
import { PersonneService } from 'app/entities/personne/personne.service';
import { Personne } from 'app/shared/model/personne.model';

describe('Component Tests', () => {
  describe('Personne Management Component', () => {
    let comp: PersonneComponent;
    let fixture: ComponentFixture<PersonneComponent>;
    let service: PersonneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PersonneComponent],
        providers: []
      })
        .overrideTemplate(PersonneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Personne(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personnes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
