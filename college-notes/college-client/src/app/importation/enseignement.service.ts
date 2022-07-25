import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enseignement } from '../models/enseignement-model';

const SERIE_API = 'http://localhost:8080/api/v1/enseignement';

@Injectable({
  providedIn: 'root'
})
export class EnseignementService {

  constructor(private http: HttpClient) {
  }

  public saveEnseignementFile(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${SERIE_API}/saveAll`,
      formData,
      {
        reportProgress: true,
        observe: 'events'
      });
  }

  public getAllEnseignement() {
    return this.http.get<Enseignement[]>(`${SERIE_API}/getAll`);
  }
}
