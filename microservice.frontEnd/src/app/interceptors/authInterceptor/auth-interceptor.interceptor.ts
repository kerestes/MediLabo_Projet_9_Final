import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { LoginService } from '../../services/loginService/login.service';
import { inject } from '@angular/core';
import { tap } from 'rxjs';

export const authInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  let loginService: LoginService = inject(LoginService);
  const token = loginService.getToken();

  if(typeof token === "string"){
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `${token}`)
    })
   req = authReq;
  }
  return next(req).pipe(tap(event => {
    if(event instanceof HttpResponse){
      let token = event.headers.get("Authorization_update")
      if(token){
        localStorage.setItem('token', token);
      }
    }
  }));
};
