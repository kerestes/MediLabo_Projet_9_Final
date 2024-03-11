import { Component, inject } from '@angular/core';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators, ControlValueAccessor } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioButton } from '@angular/material/radio';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {provideNativeDateAdapter} from '@angular/material/core';
import { Patient } from '../../models/patient/patient';
import { Adresse } from '../../models/adresse/adresse';
import { PatientService } from '../../services/patient/patient.service';

@Component({
  selector: 'app-add-patient',
  standalone: true,
  imports: [
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatButtonModule,
    MatRadioButton,
    RouterLink,
    MatCardModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatInputModule
  ],
  providers:[provideNativeDateAdapter()],
  templateUrl: './add-patient.component.html',
  styleUrl: './add-patient.component.css'
})
export class AddPatientComponent {

  patientService:PatientService = inject(PatientService);
  patient!:Patient;
  addPatient!:FormGroup;

  ngOnInit(){
    this.patient = this.patientService.patient;
    this.loadForm(this.patient);
    console.log(typeof(this.patient.id) === 'number')
  }

  loadForm(patient:Patient){
    this.addPatient = new FormGroup({
      nom: new FormControl(patient.nom, [Validators.required]),
      prenom: new FormControl(patient.prenom, [Validators.required]),
      dateNaissance: new FormControl(patient.dateNaissance ? new Date(patient.dateNaissance) : new Date(), [Validators.required]),
      genre: new FormControl(patient.genre, [Validators.required]),
      telephone: new FormControl(patient.telephone),
      adresse: new FormGroup ({
        adresse: new FormControl(patient.adresse?.adresse),
        ville: new FormControl(patient.adresse?.ville),
        codePostal: new FormControl(patient.adresse?.codePostal),
      })
    });
  }


  onSubmit(){
    if(this.addPatient.value.prenom && this.addPatient.value.nom && this.addPatient.value.dateNaissance
      && this.addPatient.value.genre){
        this.patient.nom = this.addPatient.value.nom;
        this.patient.prenom = this.addPatient.value.prenom;
        this.patient.dateNaissance = this.addPatient.value.dateNaissance.getTime();
        this.patient.genre = this.addPatient.value.genre;
      }
    if(this.addPatient.value.telephone)
      this.patient.telephone = this.addPatient.value.telephone;
    if(this.addPatient.value.adresse?.adresse && this.addPatient.value.adresse?.codePostal && this.addPatient.value.adresse?.ville){
      this.patient.adresse = new Adresse(this.addPatient.value.adresse?.adresse,
                                                this.addPatient.value.adresse?.codePostal,
                                                this.addPatient.value.adresse?.ville);
    }
    if(this.addPatient.valid){
      if(typeof(this.patient.id) === "number"){
        console.log("entrou - ediçao")
        this.patientService.updatePatient(this.patient).subscribe({
          next:(response)=>{
            console.log(response);
            this.patientService.patient = response;
          },
          error:(err) => {
            console.log(err);
          }
        });
      }
      else{
        this.patientService.createPatient(this.patient).subscribe({
          next:(response)=>{
            console.log(response);
            this.patientService.patient = response;
          },
          error:(err) => {
            console.log(err);
          }
        });;
      }

    }

  }

  verifierEtValider(){
    console.log('chamou')
    if(this.addPatient.value.adresse?.adresse || this.addPatient.value.adresse?.codePostal || this.addPatient.value.adresse?.ville){
      console.log('entrou')
      this.addPatient.get('adresse')?.get('adresse')?.setValidators([Validators.required]);
      this.addPatient.get('adresse')?.get('ville')?.setValidators([Validators.required]);
      this.addPatient.get('adresse')?.get('codePostal')?.setValidators([Validators.required]);
    } else {
      this.addPatient.get('adresse')?.get('adresse')?.clearValidators;
      this.addPatient.get('adresse')?.get('ville')?.clearValidators;
      this.addPatient.get('adresse')?.get('codePostal')?.clearValidators;
    }
    this.addPatient.get('adresse')?.get('adresse')?.updateValueAndValidity();
    this.addPatient.get('adresse')?.get('ville')?.updateValueAndValidity();
    this.addPatient.get('adresse')?.get('codePostal')?.updateValueAndValidity();
  }

}
