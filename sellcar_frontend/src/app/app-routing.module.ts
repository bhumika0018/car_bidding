import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { SignupComponent } from './auth/components/signup/signup.component';
import { DashboardComponent } from './shared/components/dashboard/dashboard.component';
import { AdminDashboardComponent } from './features/admin/components/admin-dashboard/admin-dashboard.component';
import { CustomerDashboardComponent } from './features/customer/components/customer-dashboard/customer-dashboard.component';
import { authGuard } from './auth.guard';
import { PostCarsComponent } from './features/admin/components/post-cars/post-cars.component';
import { adminGuard } from './features/admin/admin.guard';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { UserListComponent } from './features/admin/components/user-list/user-list.component';
import { MyBookingsComponent } from './features/customer/components/my-bookings/my-bookings.component';
import { BidListComponent } from './features/admin/components/bid-list/bid-list.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full',
  },
  // Dashboard route
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  // Signup route
  {
    path: 'signup',
    component: SignupComponent,
  },
  // Login route
  {
    path: 'login',
    component: LoginComponent,
  },
  // Admin dashboard route with admin guard
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [adminGuard],
  },
  // My bookings route
  {
    path: 'my-bookings',
    component: MyBookingsComponent,
    canActivate: [authGuard],
  },
  // Customer dashboard route with auth guard
  {
    path: 'customer-dashboard',
    component: CustomerDashboardComponent,
    canActivate: [authGuard],
  },
  // 404 route
  {
    path: 'not-found',
    component: NotFoundComponent,
  },
  // Post car route with admin guard
  {
    path: 'post-car',
    component: PostCarsComponent,
    canActivate: [adminGuard],
  },
  { path: 'user-list', 
    component: UserListComponent,
    canActivate: [adminGuard]
   },
  {
    path: 'bid-list',
    component: BidListComponent,
    canActivate: [adminGuard],
  },
  // Lazy-loaded admin module
  {
    path: 'admin',
    loadChildren: () =>
      import('./features/admin/admin.module').then((m) => m.AdminModule),
  },
  // Lazy-loaded customer module
  {
    path: 'customer',
    loadChildren: () =>
      import('./features/customer/customer.module').then(
        (m) => m.CustomerModule
      ),
  },
  // Wildcard route for undefined paths
  {
    path: '**',
    redirectTo: '/dashboard',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
