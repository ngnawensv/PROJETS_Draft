import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Inscription } from '../models/inscription-model';

const SERIE_API = 'http://localhost:8080/api/v1/inscription';

@Injectable({
  providedIn: 'root'
})
export class InscriptionService {

  constructor(private http: HttpClient) {
  }

  public saveInscriptionFile(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${SERIE_API}/saveAll`,
      formData,
      {
        reportProgress: true,
        observe: 'events'
      });
  }

  public getAllInscription() {
    return this.http.get<Inscription[]>(`${SERIE_API}/getAll`);
  }
}
