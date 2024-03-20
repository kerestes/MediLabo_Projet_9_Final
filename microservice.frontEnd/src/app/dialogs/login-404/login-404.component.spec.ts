import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Login404Component } from './login-404.component';

xdescribe('Login404Component', () => {
  let component: Login404Component;
  let fixture: ComponentFixture<Login404Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Login404Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Login404Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
