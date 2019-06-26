import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  RapportComponent,
  RapportDetailComponent,
  RapportUpdateComponent,
  RapportDeletePopupComponent,
  RapportDeleteDialogComponent,
  rapportRoute,
  rapportPopupRoute
} from './';

const ENTITY_STATES = [...rapportRoute, ...rapportPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RapportComponent,
    RapportDetailComponent,
    RapportUpdateComponent,
    RapportDeleteDialogComponent,
    RapportDeletePopupComponent
  ],
  entryComponents: [RapportComponent, RapportUpdateComponent, RapportDeleteDialogComponent, RapportDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationRapportModule {}
