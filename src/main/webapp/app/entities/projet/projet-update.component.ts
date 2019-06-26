import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProjet, Projet } from 'app/shared/model/projet.model';
import { ProjetService } from './projet.service';
import { IPersonne } from 'app/shared/model/personne.model';
import { PersonneService } from 'app/entities/personne';

@Component({
  selector: 'jhi-projet-update',
  templateUrl: './projet-update.component.html'
})
export class ProjetUpdateComponent implements OnInit {
  isSaving: boolean;

  personnes: IPersonne[];

  editForm = this.fb.group({
    id: [],
    name: [],
    personne: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected projetService: ProjetService,
    protected personneService: PersonneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projet }) => {
      this.updateForm(projet);
    });
    this.personneService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonne[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonne[]>) => response.body)
      )
      .subscribe((res: IPersonne[]) => (this.personnes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(projet: IProjet) {
    this.editForm.patchValue({
      id: projet.id,
      name: projet.name,
      personne: projet.personne
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projet = this.createFromForm();
    if (projet.id !== undefined) {
      this.subscribeToSaveResponse(this.projetService.update(projet));
    } else {
      this.subscribeToSaveResponse(this.projetService.create(projet));
    }
  }

  private createFromForm(): IProjet {
    return {
      ...new Projet(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      personne: this.editForm.get(['personne']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjet>>) {
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

  trackPersonneById(index: number, item: IPersonne) {
    return item.id;
  }
}
