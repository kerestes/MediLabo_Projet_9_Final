import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button'
import { RouterLink } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';

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

  login:boolean = false;

  logout():void{
  };

}
