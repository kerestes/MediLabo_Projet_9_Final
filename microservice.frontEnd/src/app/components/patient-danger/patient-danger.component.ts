import { Component, inject } from '@angular/core';
import { Risque } from '../../models/risque/risque';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { RisqueService } from '../../services/risqueService/risque.service';

@Component({
  selector: 'app-patient-danger',
  standalone: true,
  imports: [CommonModule,
    MatCardModule,
    MatTableModule],
  templateUrl: './patient-danger.component.html',
  styleUrl: './patient-danger.component.css'
})
export class PatientDangerComponent {

  risqueService:RisqueService = inject(RisqueService);

  displayedColumns: string[] = ['patient', 'risque'];
  dataSource!:Risque[];

  ngOnInit(){
    this.getPatientRisqueList();
  }

  getPatientRisqueList(){
    this.risqueService.getPatientRisqueList().subscribe({
      next:(response) => {
        console.log(response)
        this.dataSource = response;
      },
      error:(err) => {
        console.log(err);
      }
    });
  }
}
