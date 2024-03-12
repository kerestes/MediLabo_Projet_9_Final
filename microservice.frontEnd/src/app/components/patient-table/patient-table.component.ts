import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { Patient } from '../../models/patient/patient';
import { PatientService } from '../../services/patient/patient.service';
import { MatDialog } from '@angular/material/dialog';
import { AddPatientComponent } from '../../dialogs/add-patient/add-patient.component';
import { Login401Component } from '../../dialogs/login-401/login-401.component';

@Component({
  selector: 'app-patient-table',
  standalone: true,
  imports: [CommonModule,
    MatCardModule,
    MatButtonModule,
    MatTableModule],
  templateUrl: './patient-table.component.html',
  styleUrl: './patient-table.component.css'
})
export class PatientTableComponent {

  private patientService:PatientService = inject(PatientService);
  private dialog:MatDialog=inject(MatDialog);

  displayedColumns: string[] = ['nom', 'prenom', 'dateNaissance', 'idAdd', 'idRemove'];
  dataSource!:Patient[];

  ngOnInit(){
    this.getPatientList();
  }

  addPatient(){
    const dialogRef = this.dialog.open(AddPatientComponent);
    dialogRef.afterClosed().subscribe(data => {
      this.dataSource = [];
      this.getPatientList();
    })
  }

  openPatient(patient:Patient){
    this.patientService.patient = patient
    this.dialog.open(AddPatientComponent);
  }

  getPatientList(){
    this.patientService.getPatientList().subscribe({
      next:(response) => {
        this.dataSource = response;
      }
    });
  }

  deletePatient(patient:Patient){
    const conf = confirm(`Voulez-vous vraiment effacer l'utilisater ${patient.nom}`)
    if(conf == true && patient.id){
      this.patientService.deletePatient(patient.id).subscribe({
        next:res => {
          this.dataSource = [];
          this.getPatientList();
        },
        error:err => {
          console.log(err);
        }
      });
    }
  }
}
