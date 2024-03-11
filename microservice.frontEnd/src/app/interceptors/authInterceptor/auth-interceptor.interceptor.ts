import { HttpInterceptorFn } from '@angular/common/http';
import { LoginService } from '../../services/loginService/login.service';
import { inject } from '@angular/core';

export const authInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  let loginService: LoginService = inject(LoginService);
  const token = loginService.getToken();

  if(typeof token === "string"){
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    })
   return next(authReq);
  }
  return next(req);
};
