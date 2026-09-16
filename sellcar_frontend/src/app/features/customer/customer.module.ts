import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { FormsModule } from '@angular/forms';
import { MyBookingsComponent } from './components/my-bookings/my-bookings.component';

@NgModule({
  declarations: [
    CustomerDashboardComponent,
    MyBookingsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    CustomerRoutingModule
  ]
})
export class CustomerModule { }
