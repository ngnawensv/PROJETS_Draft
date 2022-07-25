import { Classe } from '../classe/classe-model';
import { Student } from './student-model';

export class Inscription {
    id: number;
    student: Student;
    classe: Classe;
}