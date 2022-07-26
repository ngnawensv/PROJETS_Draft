import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/auth/';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }


  login(credentials): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username: credentials.username,
      password: credentials.password
    },this.getHttpOptions(credentials.schoolcode));
  }

  getHttpOptions(tenantId:string){
     return {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' ,'X-TenantID':tenantId})
    };
  }

}
