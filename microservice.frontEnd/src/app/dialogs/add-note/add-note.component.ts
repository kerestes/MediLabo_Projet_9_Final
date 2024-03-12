import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioButton } from '@angular/material/radio';
import { RouterLink } from '@angular/router';
import { NoteService } from '../../services/noteService/note.service';
import { Note } from '../../models/note/note';
import { Patient } from '../../models/patient/patient';

@Component({
  selector: 'app-add-note',
  standalone: true,
  imports: [
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatButtonModule,
    MatRadioButton,
    RouterLink,
    MatCardModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatInputModule
  ],
  templateUrl: './add-note.component.html',
  styleUrl: './add-note.component.css'
})
export class AddNoteComponent {

  noteService:NoteService = inject(NoteService);

  addNote = new FormGroup({
    nom: new FormControl(this.noteService.patient.nom, [Validators.required]),
    prenom: new FormControl(this.noteService.patient.prenom , [Validators.required]),
    note: new FormControl(this.noteService.note.note, [Validators.required])
  });

  onSubmit(){
    if(this.addNote.valid && this.addNote.value.note){
      this.noteService.note.patId = this.noteService.patient.id
      this.noteService.note.patient = this.noteService.patient.nom
      this.noteService.note.note = this.addNote.value.note
    }

    this.noteService.addNote().subscribe({
      error:(err) => {
        console.log(err)
      }
    })
    this.noteService.note = new Note();
    this.noteService.patient = new Patient();
  }
}
