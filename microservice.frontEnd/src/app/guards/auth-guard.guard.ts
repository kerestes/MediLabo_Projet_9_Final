import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/loginService/login.service';
import { inject } from '@angular/core';

export const authGuardGuard: CanActivateFn = (route, state) => {
  return inject(LoginService).isAuthencitated()? true : inject(Router).navigate(['login']);
};
