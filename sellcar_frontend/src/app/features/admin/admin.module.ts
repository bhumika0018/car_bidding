import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { PostCarsComponent } from './components/post-cars/post-cars.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CarListComponent } from './components/car-list/car-list.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { BidListComponent } from './components/bid-list/bid-list.component';


@NgModule({
  declarations: [
    AdminDashboardComponent,
    PostCarsComponent,
    CarListComponent,
    UserListComponent,
    BidListComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AdminRoutingModule,
    FormsModule
  ]
})
export class AdminModule { }
