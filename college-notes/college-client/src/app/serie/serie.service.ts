import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Serie } from './serie.model';

const SERIE_API = 'http://localhost:8080/api/v1/serie';

@Injectable({
  providedIn: 'root'
})
export class SerieService {

  constructor(private http: HttpClient) {
  }

  public saveSerieFile(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${SERIE_API}/saveAll`,
      formData,
      {
        reportProgress: true,
        observe: 'events'
      });
  }

  public getAllSerie(){
    return this.http.get<Serie[]>(`${SERIE_API}/getAll`);
  }
}
