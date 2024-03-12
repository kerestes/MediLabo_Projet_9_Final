import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { praticienGuardGuard } from './praticien-guard.guard';

xdescribe('organisateurGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => praticienGuardGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
