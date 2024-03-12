import { Routes } from '@angular/router';
import { PatientTableComponent } from './components/patient-table/patient-table.component';
import { LoginComponent } from './components/login/login.component';
import { organisateurGuardGuard } from './guards/organisateur-guard/organisateur-guard.guard';
import { NoteTableComponent } from './components/note-table/note-table.component';
import { praticienGuardGuard } from './guards/praticien/praticien-guard.guard';
import { PatientDangerComponent } from './components/patient-danger/patient-danger.component';

export const routes: Routes = [
  {path:'', redirectTo:'/login', pathMatch:'full'},
  {path:'login', component: LoginComponent},
  {path:'gestion/notes', canActivate: [praticienGuardGuard],component: NoteTableComponent},
  {path:'gestion/patients', canActivate: [organisateurGuardGuard], component: PatientTableComponent},
  {path:'gestion/risque', canActivate: [praticienGuardGuard], component: PatientDangerComponent},
  {path:'**', redirectTo:'/login', pathMatch:'full'}
];
