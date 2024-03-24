import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { organisateurGuardGuard } from './organisateur-guard.guard';

xdescribe('organisateurGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => organisateurGuardGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
