import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/components/login/login.component';
import { SignupComponent } from './auth/components/signup/signup.component';
import { DashboardComponent } from './shared/components/dashboard/dashboard.component';
import { FormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AdminModule } from './features/admin/admin.module';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { CustomerModule } from './features/customer/customer.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    DashboardComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    AdminModule,
    CustomerModule
  ],
  providers: [
    provideHttpClient(
      withInterceptorsFromDi()
    ),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
