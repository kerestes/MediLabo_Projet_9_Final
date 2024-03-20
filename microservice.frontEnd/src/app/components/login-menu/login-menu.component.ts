import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button'
import { Router, RouterLink } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { DOCUMENT } from '@angular/common';
import { LoginService } from '../../services/loginService/login.service';

@Component({
  selector: 'app-login-menu',
  standalone: true,
  imports: [MatButtonModule,
    MatMenuModule,
    RouterLink],
  templateUrl: './login-menu.component.html',
  styleUrl: './login-menu.component.css'
})
export class LoginMenuComponent {

  loginService:LoginService = inject(LoginService);
  document:Document = inject(DOCUMENT);
  localStorage = this.document.defaultView?.localStorage;

  login:boolean = this.loginService.isAuthenticated();
  role:string | null = this.loginService.witchRole();

  logout():void{
    if(this.localStorage){
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      window.location.replace("/");
    }
  };

}
