import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Note } from '../../models/note/note';
import { Patient } from '../../models/patient/patient';

export const NOTE_URL: string = "http://localhost:9001/notes"

@Injectable({
  providedIn: 'root'
})
export class NoteService {


  note:Note = new Note();
  patient:Patient = new Patient();
  addPatient:boolean = true;

  constructor(private http: HttpClient) { }

  getNoteList(){
    return this.http.get<Array<Note>>(NOTE_URL);
  }

  getPatientList(){
    return this.http.get<Array<Patient>>(`${NOTE_URL}/patlist`);
  }

  addNote(){
    return this.http.post<Note>(`${NOTE_URL}`, this.note);
  }

  deleteNote(noteId:number){
    return this.http.delete(`${NOTE_URL}/${noteId}`);
  }
}
