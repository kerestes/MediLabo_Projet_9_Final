import { Routes } from '@angular/router';
import { PatientTableComponent } from './components/patient-table/patient-table.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
  {path:'', redirectTo:'/login', pathMatch:'full'},
  {path:'login', component: LoginComponent},
  {path:'gestion/patients', component: PatientTableComponent},
  {path:'**', redirectTo:'/login', pathMatch:'full'}
];
