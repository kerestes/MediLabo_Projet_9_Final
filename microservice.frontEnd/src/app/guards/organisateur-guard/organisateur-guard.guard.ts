import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../../services/loginService/login.service';
import { inject } from '@angular/core';

export const organisateurGuardGuard: CanActivateFn = (route, state) => {
  return inject(LoginService).isAuthencitatedAsOrganizateur()? true : inject(Router).navigate(['login']);
};
