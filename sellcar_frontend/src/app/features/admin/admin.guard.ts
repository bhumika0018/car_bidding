import { CanActivateFn, Router } from '@angular/router';

export const adminGuard: CanActivateFn = (route, state) => {
  const router: Router = new Router();
    if ( localStorage.getItem('jwt') && localStorage.getItem('email')==='admin@test.com') {
      return true;
    } else {
      router.navigate(['/not-found']);
      return false;
    }
};
