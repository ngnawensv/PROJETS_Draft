
import { Cours } from '../models/cours-model';
import { Serie } from '../serie/serie.model'

export class Classe {
    id: number;
    level: string;
    serie: Serie;
    name: string;
    listOfCours: Array<Cours>;
}