import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: false,
  
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  constructor(private router:Router, private adminService: AdminService){ 

  }

  isLoggedIn: boolean = false;
  bids: any[] = [];
  isSearchedCars: boolean = false;
  isDropdownOpen: boolean = false;
  
  cars: any[] = [];
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
  ngOnInit() : void
  {
    this.isLoggedIn = !!localStorage.getItem('jwt');
  }
  ngDoCheck() : void
  {
    this.isLoggedIn = !!localStorage.getItem('jwt');
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
  logout(): void
  {
    localStorage.clear();
    sessionStorage.clear();
    alert('Logged out successfully');
    window.location.href = '/dashboard'; 
    this.isLoggedIn = false;
  }
  getCarImage(car: any): string {
    return car.returnedImg
      ? `data:image/jpeg;base64,${car.returnedImg}`
      : 'public/default-car.jpg';
  }
  
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


    this.adminService.searchCarAdmin(this.searchCriteria).subscribe({
      next: (response) => {
        this.cars = response;
        console.log("Response:",response);
        this.searchCriteria = {
          brand: null,
          name: null,
          price: null,
          transmission: null,
          type: null,
          color: null
        };
      },
      error: (err) => {
        console.error('Error searching cars:', err);
      }
    });
  }
}
