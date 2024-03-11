import { HttpClient } from '@angular/common/http';
import { DOCUMENT } from '@angular/common';
import { Injectable, inject } from '@angular/core';
import { JwtHelperService} from '@auth0/angular-jwt';
import { catchError } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { Login401Component } from '../../dialogs/login-401/login-401.component';
import { Login404Component } from '../../dialogs/login-404/login-404.component';
import { UserLogin } from '../../models/user-login/user-login';
import { ResponseLogin } from '../../models/response-login/response-login';

export const LOGIN_URL: string = "http://localhost:9001/auth"

export const jwtHelper = new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  role:string = 'none';
  document:Document = inject(DOCUMENT);
  localStorage = this.document.defaultView?.localStorage;
  private dialog:MatDialog=inject(MatDialog);

  constructor(private http:HttpClient) { }

  isAuthencitatedAsOrganizateur():boolean{
    return this.role === 'ORGANISATEUR'
  }

  isAuthencitatedAsPraticien():boolean{
    return this.role === 'PRATICIEN'
  }

  submit(loginCredential: UserLogin){
    return this.http.post<ResponseLogin>(`${LOGIN_URL}/login`, loginCredential);
  }

  getToken(){
    if(this.localStorage){
      return localStorage.getItem("token");
    }
    return null;
  }
}
