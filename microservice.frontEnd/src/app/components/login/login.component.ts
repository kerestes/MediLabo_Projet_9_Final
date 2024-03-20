import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators} from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { LoginService } from '../../services/loginService/login.service';
import { MatDialog } from '@angular/material/dialog';
import { Login401Component } from '../../dialogs/login-401/login-401.component';
import { UserLogin } from '../../models/user-login/user-login';
import { Login404Component } from '../../dialogs/login-404/login-404.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSnackBarModule,
    RouterLink,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  matSnackBar = inject(MatSnackBar);
  loginService: LoginService = inject(LoginService);
  router:Router = inject(Router);
  private dialog:MatDialog=inject(MatDialog);
  private userLogin:UserLogin = new UserLogin;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  hidePassword:boolean = true;

  tooglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;
  }

  onSubmit(){
    if(this.loginForm.value.email && this.loginForm.value.password){
      this.userLogin.username = this.loginForm.value.email;
      this.userLogin.password = this.loginForm.value.password;

      this.loginService.submit(this.userLogin).subscribe({
        next:(response) => {
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role);
          if(response.role === 'ORGANISATEUR'){
            window.location.replace("/gestion/patients");
          } else if (response.role === 'PRATICIEN'){
            window.location.replace("/gestion/notes");
          }
        },
        error:(err)=>{
          if(err.status == 404)
            this.dialog.open(Login404Component)
        }
      });
    }

  }

}
