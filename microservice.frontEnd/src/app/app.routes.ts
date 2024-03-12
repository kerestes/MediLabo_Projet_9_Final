import { Routes } from '@angular/router';
import { PatientTableComponent } from './components/patient-table/patient-table.component';
import { LoginComponent } from './components/login/login.component';
import { organisateurGuardGuard } from './guards/organisateur-guard/organisateur-guard.guard';
import { NoteTableComponent } from './components/note-table/note-table.component';
import { praticienGuardGuard } from './guards/praticien/praticien-guard.guard';

export const routes: Routes = [
  {path:'', redirectTo:'/login', pathMatch:'full'},
  {path:'login', component: LoginComponent},
  {path:'gestion/notes', canActivate: [praticienGuardGuard],component: NoteTableComponent},
  {path:'gestion/patients', canActivate: [organisateurGuardGuard], component: PatientTableComponent},
  {path:'**', redirectTo:'/login', pathMatch:'full'}
];
