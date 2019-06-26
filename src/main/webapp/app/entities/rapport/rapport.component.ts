import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRapport } from 'app/shared/model/rapport.model';
import { AccountService } from 'app/core';
import { RapportService } from './rapport.service';

@Component({
  selector: 'jhi-rapport',
  templateUrl: './rapport.component.html'
})
export class RapportComponent implements OnInit, OnDestroy {
  rapports: IRapport[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected rapportService: RapportService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.rapportService
      .query()
      .pipe(
        filter((res: HttpResponse<IRapport[]>) => res.ok),
        map((res: HttpResponse<IRapport[]>) => res.body)
      )
      .subscribe(
        (res: IRapport[]) => {
          this.rapports = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRapports();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRapport) {
    return item.id;
  }

  registerChangeInRapports() {
    this.eventSubscriber = this.eventManager.subscribe('rapportListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
