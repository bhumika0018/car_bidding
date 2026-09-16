import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-my-bookings',
  standalone: false,
  templateUrl: './my-bookings.component.html',
  styleUrl: './my-bookings.component.css',
})
export class MyBookingsComponent implements OnInit {
  bids: any[] = [];
  selectedCar: any = null;
  userId: number = Number(localStorage.getItem('userid'));
  carDetailsMap: Map<number, any> = new Map(); // Stores car details
  isLoaded: boolean = false;

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    if (this.userId) {
      this.fetchUserBids();
    }
  }

  fetchUserBids(): void {
    this.isLoaded = false;
    this.customerService.getBidsByUser(this.userId).subscribe({
      next: (response) => {
        this.bids = response;
        this.isLoaded = true;
        this.fetchCarDetails();
      },
      error: (error) => {
        console.error('Error fetching bids:', error);
        this.isLoaded = true;
      },
    });
  }

  fetchCarDetails(): void {
    this.bids.forEach((bid) => {
      if (bid.carId && !this.carDetailsMap.has(bid.carId)) {
        this.customerService.getCarByIdCustomer(bid.carId).subscribe({
          next: (car) => {
            this.carDetailsMap.set(bid.carId, car);
          },
          error: (error) => {
            console.error(
              `Error fetching car details for carId ${bid.carId}:`,
              error
            );
          },
        });
      }
    });
  }

  getCarName(carId: number): string {
    return this.carDetailsMap.has(carId)
      ? this.carDetailsMap.get(carId)?.name ?? 'Unknown Car'
      : 'Loading...';
  }

  openModal(car: any): void {
    this.selectedCar = { ...car }; // Ensure we create a new reference
    console.log(this.selectedCar);
  }

  closeModal(): void {
    this.selectedCar = null;
  }
  getCarDetails(carId: number): void {
    if (this.carDetailsMap.has(carId)) {
      this.selectedCar = { ...this.carDetailsMap.get(carId) };
    } else {
      this.customerService.getCarByIdCustomer(carId).subscribe({
        next: (car) => {
          this.carDetailsMap.set(carId, car);
          this.selectedCar = { ...car };
        },
        error: (error) => {
          console.error(
            `Error fetching car details for carId ${carId}:`,
            error
          );
        },
      });
    }
  }

  getBidId(carId: number): number {
    const bid = this.bids.find((b) => b.carId === carId);
    return bid ? bid.bidId : 0;
  }

  deleteBid(carId: number): void {
    const bid = this.bids.find((bid) => bid.carId === carId);

    if (!bid) {
      console.error('Bid not found for carId:', carId);
      return;
    }

    const bidId = bid.bidId;

    if (confirm('Are you sure you want to delete this bid?')) {
      console.log('Deleting bid with ID:', bidId);
      this.customerService.deleteBid(bidId).subscribe({
        next: () => {
          this.bids = this.bids.filter((b) => b.bidId !== bidId);
          console.log('Updated Bids:', this.bids);
        },
        error: (error) => {
          console.error('Error deleting bid:', error.message || error);
          alert('Error deleting bid.');
        },
      });
    }
  }
}
