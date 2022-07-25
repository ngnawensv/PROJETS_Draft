import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from '../models/role-model';

const SERIE_API = 'http://localhost:8080/api/v1/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) {
  }

  public saveRoleFile(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${SERIE_API}/saveAll`,
      formData,
      {
        reportProgress: true,
        observe: 'events'
      });
  }

  public getAllRole() {
    return this.http.get<Role[]>(`${SERIE_API}/getAll`);
  }
}
