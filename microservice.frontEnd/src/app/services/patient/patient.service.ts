import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Patient } from '../../models/patient/patient';
import { environment } from '../../../environments/environment';

export const PATIENT_URL: string = `http://${environment.PATIENT}/patient`

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  patient:Patient = new Patient();
  constructor(private http: HttpClient) { }

  getPatientList(){
    return this.http.get<Array<Patient>>(`${PATIENT_URL}`);
  }

  createPatient(patient:Patient){
    return this.http.post<Patient>(`${PATIENT_URL}`, patient);
  }

  updatePatient(patient:Patient){
    return this.http.put<Patient>(`${PATIENT_URL}`, patient);
  }

  deletePatient(id:number){
    return this.http.delete(`${PATIENT_URL}/${id}`);
  }
}
