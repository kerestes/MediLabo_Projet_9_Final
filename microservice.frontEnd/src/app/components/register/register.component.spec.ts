import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { RegisterService } from '../../services/registerService/register.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [RegisterService],
      imports: [RegisterComponent, HttpClientTestingModule, BrowserAnimationsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should submit the form', ()=> {
    spyOn(component, 'onSubmit')
    component.signupForm.get('nom')?.setValue("Dupont");
    component.signupForm.get('prenom')?.setValue("Robert");
    component.signupForm.get('email')?.setValue("robert@gmail.com");
    component.signupForm.get('password')?.setValue('123456789');
    component.signupForm.get('confirmPassword')?.setValue('123456789');
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;

    const els = compiled.querySelector('#submit');
    els?.dispatchEvent(new Event('click'));

    expect(component.onSubmit).toHaveBeenCalled();
  })

  it('should show the password', ()=> {
    spyOn(component, 'tooglePasswordVisibility').and.callThrough();
    component.signupForm.get('password')?.setValue('123456789');
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('#password').type).toBe('password');

    const els = fixture.debugElement.nativeElement.querySelector('#passwordVisibility');
    els.click();
    fixture.detectChanges();

    expect(component.tooglePasswordVisibility).toHaveBeenCalled();
    expect(fixture.nativeElement.querySelector('#password').type).toBe('text');
  })
});
