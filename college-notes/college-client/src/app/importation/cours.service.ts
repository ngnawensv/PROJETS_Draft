import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cours } from '../models/cours-model';

const SERIE_API = 'http://localhost:8080/api/v1/cours';

@Injectable({
  providedIn: 'root'
})
export class CoursService {

  constructor(private http: HttpClient) {
  }

  public saveCoursFile(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${SERIE_API}/saveAll`,
      formData,
      {
        reportProgress: true,
        observe: 'events'
      });
  }

  public getAllCours() {
    return this.http.get<Cours[]>(`${SERIE_API}/getAll`);
  }

}
