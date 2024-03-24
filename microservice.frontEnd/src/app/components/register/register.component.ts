import { Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RegisterService } from '../../services/registerService/register.service';
import { passwordValidator } from '../../validators/PasswordValidator';
import { MatDialog } from '@angular/material/dialog';
import { RegistrationSuccessComponent } from '../../dialogs/registration-success/registration-success.component';

@Component({
  selector: 'app-registrer',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSnackBarModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
[x: string]: any;

  private registerService: RegisterService=inject(RegisterService);
  private dialog:MatDialog=inject(MatDialog);
  snackBar=inject(MatSnackBar);

  signupForm:FormGroup = new FormGroup({
    nom: new FormControl(null, Validators.required),
    prenom: new FormControl(null, Validators.required),
    email: new FormControl(null, [Validators.required, Validators.email]),
    adresse: new FormControl(null),
    password: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    confirmPassword: new FormControl(null, [Validators.required, Validators.minLength(8)])
  }, {
    validators: passwordValidator
  });

  hidenPassword:boolean = true;

  get password(){return this.signupForm.get('password')};
  get confirmPassword(){return this.signupForm.get('confirmPassword')};

  tooglePasswordVisibility():void{
    this.hidenPassword = !this.hidenPassword;
  }

  get form(){
    return this.signupForm.controls;
  }

  onSubmit():void{
      this.registerService.register(this.signupForm.value).subscribe({
        next:(errors) => {
          if(errors[0] == "success"){
            this.dialog.open(RegistrationSuccessComponent);
          } else {
            //cria modal
            errors.forEach( erro => console.log(erro));
          }
        }
      });
  }
}
