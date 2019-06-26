import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonne } from 'app/shared/model/personne.model';
import { AccountService } from 'app/core';
import { PersonneService } from './personne.service';

@Component({
  selector: 'jhi-personne',
  templateUrl: './personne.component.html'
})
export class PersonneComponent implements OnInit, OnDestroy {
  personnes: IPersonne[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personneService: PersonneService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personneService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonne[]>) => res.ok),
        map((res: HttpResponse<IPersonne[]>) => res.body)
      )
      .subscribe(
        (res: IPersonne[]) => {
          this.personnes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonnes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonne) {
    return item.id;
  }

  registerChangeInPersonnes() {
    this.eventSubscriber = this.eventManager.subscribe('personneListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
