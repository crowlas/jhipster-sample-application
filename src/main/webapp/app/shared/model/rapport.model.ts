import { IProjet } from 'app/shared/model/projet.model';
import { IPersonne } from 'app/shared/model/personne.model';

export interface IRapport {
  id?: number;
  month?: string;
  meteo?: string;
  resume?: string;
  projet?: IProjet;
  redacteur?: IPersonne;
}

export class Rapport implements IRapport {
  constructor(
    public id?: number,
    public month?: string,
    public meteo?: string,
    public resume?: string,
    public projet?: IProjet,
    public redacteur?: IPersonne
  ) {}
}
