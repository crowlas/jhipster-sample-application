import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'projet',
        loadChildren: './projet/projet.module#JhipsterSampleApplicationProjetModule'
      },
      {
        path: 'rapport',
        loadChildren: './rapport/rapport.module#JhipsterSampleApplicationRapportModule'
      },
      {
        path: 'personne',
        loadChildren: './personne/personne.module#JhipsterSampleApplicationPersonneModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
