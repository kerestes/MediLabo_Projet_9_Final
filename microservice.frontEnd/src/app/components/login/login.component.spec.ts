import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { LoginService } from '../../services/loginService/login.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [LoginService],
      imports: [LoginComponent, HttpClientTestingModule, RouterTestingModule, BrowserAnimationsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should submit the form', ()=> {
    spyOn(component, 'onSubmit')
    component.loginForm.get('email')?.setValue("alexandre@gmail.com");
    component.loginForm.get('password')?.setValue('123456789');
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;

    const els = compiled.querySelector('#submit');
    els?.dispatchEvent(new Event('click'));

    expect(component.onSubmit).toHaveBeenCalled();
  })

  it('should show the password', ()=> {
    spyOn(component, 'tooglePasswordVisibility').and.callThrough();
    component.loginForm.get('password')?.setValue('123456789');
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('#password').type).toBe('password');

    const els = fixture.debugElement.nativeElement.querySelector('#passwordVisibility');
    els.click();
    fixture.detectChanges();

    expect(component.tooglePasswordVisibility).toHaveBeenCalled();
    expect(fixture.nativeElement.querySelector('#password').type).toBe('text');
  })
});
