import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Login401Component } from './login-401.component';

xdescribe('Login401Component', () => {
  let component: Login401Component;
  let fixture: ComponentFixture<Login401Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Login401Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Login401Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
