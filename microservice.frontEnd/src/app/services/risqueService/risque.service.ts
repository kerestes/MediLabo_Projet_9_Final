import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Risque } from '../../models/risque/risque';
import { environment } from '../../../environments/environment';

export const PATIENT_URL: string = `http://${environment.RISQUE}/risques`

@Injectable({
  providedIn: 'root'
})
export class RisqueService {

  constructor( private http:HttpClient) { }

  getPatientRisqueList(){
    return this.http.get<Array<Risque>>(PATIENT_URL);
  }
}
