import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientDangerComponent } from './patient-danger.component';

describe('PatientDangerComponent', () => {
  let component: PatientDangerComponent;
  let fixture: ComponentFixture<PatientDangerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatientDangerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PatientDangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
