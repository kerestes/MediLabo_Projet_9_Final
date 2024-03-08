import { Routes } from '@angular/router';
import { PatientTableComponent } from './components/patient-table/patient-table.component';

export const routes: Routes = [
  {path:'', redirectTo:'/patient-table', pathMatch:'full'},
  {path:'patient-table', component: PatientTableComponent},
];
