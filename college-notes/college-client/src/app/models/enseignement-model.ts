import { Classe } from '../classe/classe-model';
import { Cours } from './cours-model';
import { Teacher } from './teacher-model';


export class Enseignement {
    id: number;
    cours: Cours;
    classe:Classe;
    teacher:Teacher
}