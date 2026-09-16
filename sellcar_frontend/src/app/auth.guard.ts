import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router); // Inject Router properly

  if (localStorage.getItem('jwt')) {
    return true;
  } else {
    router.navigate(['/dashboard']);
    return false;
  }
};

