import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../models/user/user';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

export const REGISTER_URL: string = "http://localhost:8080/registration"

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  register(user: User):Observable<Array<string>>{
    return this.http.post<Array<string>>(REGISTER_URL, user);
  }

  confirmRegistration(token: string){
    return this.http.get(`${REGISTER_URL}/confirm?token=${token}`);
  }
}
