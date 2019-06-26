import { IProjet } from 'app/shared/model/projet.model';

export interface IPersonne {
  id?: number;
  name?: string;
  responsableDes?: IProjet[];
}

export class Personne implements IPersonne {
  constructor(public id?: number, public name?: string, public responsableDes?: IProjet[]) {}
}
