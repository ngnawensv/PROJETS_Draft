import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Classe } from '../classe/classe-model';
const SERIE_API = 'http://localhost:8080/api/v1/classe';

@Injectable({
  providedIn: 'root'
})
export class ClasseService {

  constructor(private http: HttpClient) {
  }

  public saveClasseFile(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${SERIE_API}/saveAll`,
      formData,
      {
        reportProgress: true,
        observe: 'events'
      });
  }

  public getAllClasse() {
    return this.http.get<Classe[]>(`${SERIE_API}/getAll`);
  }
}
