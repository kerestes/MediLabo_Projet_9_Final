import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../../services/loginService/login.service';
import { inject } from '@angular/core';

export const praticienGuardGuard: CanActivateFn = (route, state) => {
  return inject(LoginService).isAuthencitatedAsPraticien()? true : inject(Router).navigate(['login']);
};
