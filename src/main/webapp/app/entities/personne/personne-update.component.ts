import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPersonne, Personne } from 'app/shared/model/personne.model';
import { PersonneService } from './personne.service';

@Component({
  selector: 'jhi-personne-update',
  templateUrl: './personne-update.component.html'
})
export class PersonneUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected personneService: PersonneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personne }) => {
      this.updateForm(personne);
    });
  }

  updateForm(personne: IPersonne) {
    this.editForm.patchValue({
      id: personne.id,
      name: personne.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personne = this.createFromForm();
    if (personne.id !== undefined) {
      this.subscribeToSaveResponse(this.personneService.update(personne));
    } else {
      this.subscribeToSaveResponse(this.personneService.create(personne));
    }
  }

  private createFromForm(): IPersonne {
    return {
      ...new Personne(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonne>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
