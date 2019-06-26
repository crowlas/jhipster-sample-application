import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRapport, Rapport } from 'app/shared/model/rapport.model';
import { RapportService } from './rapport.service';
import { IProjet } from 'app/shared/model/projet.model';
import { ProjetService } from 'app/entities/projet';
import { IPersonne } from 'app/shared/model/personne.model';
import { PersonneService } from 'app/entities/personne';

@Component({
  selector: 'jhi-rapport-update',
  templateUrl: './rapport-update.component.html'
})
export class RapportUpdateComponent implements OnInit {
  isSaving: boolean;

  projets: IProjet[];

  redacteurs: IPersonne[];

  editForm = this.fb.group({
    id: [],
    month: [],
    meteo: [],
    resume: [],
    projet: [],
    redacteur: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected rapportService: RapportService,
    protected projetService: ProjetService,
    protected personneService: PersonneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rapport }) => {
      this.updateForm(rapport);
    });
    this.projetService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjet[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjet[]>) => response.body)
      )
      .subscribe((res: IProjet[]) => (this.projets = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personneService
      .query({ filter: 'rapport-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonne[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonne[]>) => response.body)
      )
      .subscribe(
        (res: IPersonne[]) => {
          if (!this.editForm.get('redacteur').value || !this.editForm.get('redacteur').value.id) {
            this.redacteurs = res;
          } else {
            this.personneService
              .find(this.editForm.get('redacteur').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPersonne>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPersonne>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPersonne) => (this.redacteurs = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(rapport: IRapport) {
    this.editForm.patchValue({
      id: rapport.id,
      month: rapport.month,
      meteo: rapport.meteo,
      resume: rapport.resume,
      projet: rapport.projet,
      redacteur: rapport.redacteur
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rapport = this.createFromForm();
    if (rapport.id !== undefined) {
      this.subscribeToSaveResponse(this.rapportService.update(rapport));
    } else {
      this.subscribeToSaveResponse(this.rapportService.create(rapport));
    }
  }

  private createFromForm(): IRapport {
    return {
      ...new Rapport(),
      id: this.editForm.get(['id']).value,
      month: this.editForm.get(['month']).value,
      meteo: this.editForm.get(['meteo']).value,
      resume: this.editForm.get(['resume']).value,
      projet: this.editForm.get(['projet']).value,
      redacteur: this.editForm.get(['redacteur']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRapport>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProjetById(index: number, item: IProjet) {
    return item.id;
  }

  trackPersonneById(index: number, item: IPersonne) {
    return item.id;
  }
}
