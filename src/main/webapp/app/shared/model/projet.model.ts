import { IRapport } from 'app/shared/model/rapport.model';
import { IPersonne } from 'app/shared/model/personne.model';

export interface IProjet {
  id?: number;
  name?: string;
  rapports?: IRapport[];
  personne?: IPersonne;
}

export class Projet implements IProjet {
  constructor(public id?: number, public name?: string, public rapports?: IRapport[], public personne?: IPersonne) {}
}
