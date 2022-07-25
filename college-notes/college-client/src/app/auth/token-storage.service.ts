import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const TENANT_KEY = 'X-TenantID';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() {
    return JSON.parse(sessionStorage.getItem(USER_KEY));
  }

  public saveTenant(tenantId) {
    window.sessionStorage.removeItem(TENANT_KEY);
    window.sessionStorage.setItem(TENANT_KEY, tenantId);
  }

  public getTenant(): string {
    return sessionStorage.getItem(TENANT_KEY);
  }

  public getRolesForCurrentUser(): string[] {
    let user = this.getUser();
    let result = [];
    if (user) {
      result = user.roles;
    }

    return result;
  }
}
