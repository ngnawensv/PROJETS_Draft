import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Evaluation } from './evaluation/evaluation.model';

const BASE_API = 'http://localhost:8080/api/v1/note';
@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  constructor(private http: HttpClient) { }

  getNoteEvaluation(classeId:string,enseignementId:string,evaluation:string) {
    return this.http.get<Evaluation[]>(`${BASE_API}/evaluation/${classeId}/${enseignementId}/${evaluation}`);
  }
}
