import { ChangeDetectorRef, Component, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorIntl, MatPaginatorModule, PageEvent } from '@angular/material/paginator'
import { MatSelectModule } from '@angular/material/select';
import { Note } from '../../models/note/note';
import { NoteService } from '../../services/noteService/note.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Patient } from '../../models/patient/patient';
import { AddNoteComponent } from '../../dialogs/add-note/add-note.component';
import { error } from 'console';

@Component({
  selector: 'app-note-table',
  standalone: true,
  imports: [CommonModule,
    MatCardModule,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSelectModule,
    ReactiveFormsModule],
  templateUrl: './note-table.component.html',
  styleUrl: './note-table.component.css'
})
export class NoteTableComponent {

  tablesize:number = 0
  pageIndex:number = 0
  notesList:Array<Note> = []
  patients:Array<Patient> = []

  @ViewChild(MatPaginator) paginator: MatPaginator =  new MatPaginator(new MatPaginatorIntl(), ChangeDetectorRef.prototype);
  private noteService:NoteService = inject(NoteService);
  private dialog:MatDialog=inject(MatDialog);

  displayedColumns: string[] = ['patient', 'note', 'idAdd', 'idRemove'];
  dataSource!:MatTableDataSource<Note>;

  newNoteForm = new FormGroup({
    patient: new FormControl(0, [Validators.required])
  });

  ngOnInit(){
    this.getNoteList();
    this.getPatientList();
  }

  addNote(){
    if(this.newNoteForm.value.patient){
      const patient:Patient|undefined = this.patients.find(p => {
        return p.id && p.id == this.newNoteForm.value.patient
      });
      if(patient){
        this.noteService.patient = patient
      }
      const dialogRef = this.dialog.open(AddNoteComponent);
      dialogRef.afterClosed().subscribe(()=>
        this.getNoteList()
      );
    }
  }

  openNote(note:Note){
    const patient:Patient | undefined = this.patients.find(p => {
      return p.id == note.patId;
    })
    if(patient){
      this.noteService.patient = patient
    }
    this.noteService.note = note;
    const dialogRef = this.dialog.open(AddNoteComponent);
      dialogRef.afterClosed().subscribe(()=>
        this.getNoteList()
      );
  }

  getNoteList(){
    this.noteService.getNoteList().subscribe({
      next:reponse => {
        console.log("entrou de novo")
        this.dataSource = new MatTableDataSource(reponse);
        this.dataSource.paginator = this.paginator;
        this.tablesize = reponse.length
      }
    })
  }

  getPatientList(){
    this.noteService.getPatientList().subscribe({
      next:response => {
        this.patients = response;
      }
    })
  }

  deleteNote(noteId:number){
    const conf = confirm(`Voulez-vous vraiment effacer cette note ?`)
    if(conf == true){
      this.noteService.deleteNote(noteId).subscribe({
        next:(response) => {
          console.log('entrou')
          window.location.replace("/gestion/notes")
        },
        error:(err)=>{
          console.log(err);
        }
      });
    }
  }
}
