import { Component, OnInit, DoCheck } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-customer-dashboard',
  standalone: false,
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.css',
})
export class CustomerDashboardComponent implements OnInit, DoCheck {
  isLoggedIn: boolean = false;
  userId: number = Number(localStorage.getItem('userid'));
  isSearchedCars: boolean = false;

  cars: any[] = [];
  selectedCar: any = null;
  bidAmount: any = null;

  searchCriteria: any = {
    brand: null,
    name: null,
    price: null,
    transmission: null,
    type:null,
    color: null
  };


  transmissions = ["Semi-Automatic", "Automatic"];
  colors = ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
  brands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA", "HAVAL"]; 
  types = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  constructor(private router: Router, private customerService: CustomerService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!localStorage.getItem('jwt');
  }

  ngDoCheck(): void {
    this.isLoggedIn = !!localStorage.getItem('jwt');
  }
  
  logout(): void
  {
    localStorage.clear();
    sessionStorage.clear();
    alert('Logged out successfully');
    window.location.href = '/dashboard'; 
    this.isLoggedIn = false;
  }

  placeBid(): void {
    const bidData = {
      price: this.bidAmount,
      carId: this.selectedCar.carId,
      userId: this.userId
    };
  
    this.customerService.placeBid(bidData).subscribe({
      next: (response) => {
        console.log(response);
        alert('Bid placed successfully!');
        this.closeModal();
      },
      error: (err) => {
        console.error('Error placing bid:', err);
        alert('Failed to place bid.');
      }
    });
  }
  
  openModal(car: any): void {
    this.selectedCar = car;
  }
  
  closeModal(): void {
    this.selectedCar = null;
    this.bidAmount = null;
  }
  

  // Fetch all available cars for customers
  getAllCarsCustomer(): void {
    this.customerService.getAllCarsCustomer().subscribe({
      next: (response) => {
        this.isSearchedCars = true;
        this.cars = response;
        console.log(response);
        
      },
      error: (err) => {
        console.error('Error fetching cars:', err);
      }
    });
  }

  // Get details of a specific car
  getCarByIdCustomer(carId: number): void {
    this.customerService.getCarByIdCustomer(carId).subscribe({
      next: (response) => {
        this.selectedCar = response;
      },
      error: (err) => {
        console.error(`Error fetching car with ID ${carId}:`, err);
      }
    });
  }

  // Search for cars
  searchCars(): void {
    this.isSearchedCars = true;
    this.searchCriteria = {
      brand: this.searchCriteria.brand?.toLowerCase(),
      name: this.searchCriteria.name?.toLowerCase(),
      price: this.searchCriteria.price,
      transmission: this.searchCriteria.transmission?.toLowerCase(),
      type: this.searchCriteria.type?.toLowerCase(),
      color: this.searchCriteria.color?.toLowerCase()
    };
    console.log(this.searchCriteria);
    this.customerService.searchCarCustomer(this.searchCriteria).subscribe({
      next: (response) => {
        this.cars = response; // Update cars list with search results
        this.searchCriteria = {
          brand: null,
          name: null,
          price: null,
          transmission: null,
          type:null,
          color: null
        };
      },
      error: (err) => {
        console.error('Error searching cars:', err);
      }
    });
  }
}
