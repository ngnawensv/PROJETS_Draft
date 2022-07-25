import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ClasseEnseignement } from './models/classe-enseignement-model';

const BASE_API = 'http://localhost:8080/api/v1/enseignement/teacher';

@Injectable({
  providedIn: 'root'
})

export class EnseignementService {

  constructor(private http: HttpClient) { }

  public getEnseignementForTeacher(username:string) {
    return this.http.get<ClasseEnseignement[]>(`${BASE_API}/${username}`);
  }
}
