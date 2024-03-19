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
import { environment } from '../../../environments/environment';

export const LOGIN_URL: string =  `http://${environment.AUTH}/auth`

export const jwtHelper = new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  login:boolean = false;
  role:string | null = 'none';
  document:Document = inject(DOCUMENT);
  localStorage = this.document.defaultView?.localStorage;
  private dialog:MatDialog=inject(MatDialog);

  constructor(private http:HttpClient) { }

  isAuthencitatedAsOrganizateur():boolean{
    if(this.role == 'none'){
      if(this.localStorage){
        this.role = localStorage.getItem("role");
      }
    }
    return this.role === 'ORGANISATEUR'
  }

  isAuthencitatedAsPraticien():boolean{
    if(this.role == 'none'){
      if(this.localStorage){
        this.role = localStorage.getItem("role");
      }
    }
    return this.role === 'PRATICIEN'
  }

  isAuthenticated():boolean{
    if(this.isAuthencitatedAsOrganizateur() || this.isAuthencitatedAsPraticien())
      this.login = true
    else
      this.login = false
    return this.login;
  }

  witchRole(){
    return this.role;
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
