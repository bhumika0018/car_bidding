import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-car-list',
  standalone: false,
  templateUrl: './car-list.component.html',
  styleUrl: './car-list.component.css'
})
export class CarListComponent {
  cars: any[] = [];
  selectedCar: any = null; // Stores car details for modal
  editMode: boolean = false; // Tracks if we are in edit mode
  editCarForm: FormGroup; // Form for editing car details

  constructor(private adminService: AdminService, private fb: FormBuilder) {
    this.editCarForm = this.fb.group({
      brand: [''],
      name: ['', [Validators.pattern(/^[a-zA-Z0-9\s]+$/)]],
      type: [''],
      transmission: [''],
      color: [''],
      year: ['', [Validators.min(1990), Validators.max(2025)]],
      price: ['', [Validators.min(1000)]],
      description: ['', [Validators.minLength(10), Validators.maxLength(500)]]
    });
  }

  ngOnInit(): void {
    this.fetchCars();
  }

  fetchCars(): void {
    this.adminService.getAllCarsAdmin().subscribe(
      (response) => {
        console.log('Fetched Cars:', response);
        this.cars = response;
      },
      (error) => {
        console.error('Error fetching cars:', error);
      }
    );
  }

  getCarImage(car: any): string {
    return car.returnedImg
      ? `data:image/jpeg;base64,${car.returnedImg}`
      : 'public/default-car.jpg';
  }

  openModal(car: any): void {
    this.selectedCar = { ...car }; // Ensure we create a new reference
    this.editMode = false; // Ensure edit mode is off initially
    console.log(this.selectedCar);
  }

  closeModal(): void {
    this.selectedCar = null;
    this.editMode = false;
  }

  enableEditMode(): void {
    if (this.selectedCar) {
      this.editMode = true;
      this.editCarForm.patchValue(this.selectedCar);
    } else {
      console.error("No car selected for editing.");
    }
  }

  saveUpdatedCar(): void {
    if (!this.selectedCar) {
      console.error("Error: No car selected for update.");
      return;
    }

    if (this.editCarForm.invalid) {
      alert('Please correct the errors before submitting.');
      return;
    }

    const updatedCarData = this.editCarForm.value;

    console.log('Updating car:', this.selectedCar.carId, updatedCarData);
    
    this.adminService.updateCar(this.selectedCar.carId, updatedCarData).subscribe(
      (response) => {
        alert('Car details updated successfully');
        this.fetchCars(); // Refresh the car list after update
        console.log('Car details',updatedCarData);
        this.closeModal();
      },
      (error) => {
        console.error('Error updating car:', error);
      }
    );
  }

  deleteCar(): void {
    if (!this.selectedCar) {
      console.error("Error: No car selected for deletion.");
      return;
    }

    if (confirm('Are you sure you want to delete this car?')) {
      console.log("Deleting car with ID:", this.selectedCar.carId);

      this.adminService.deleteCar(this.selectedCar.carId).subscribe(
        () => {
          alert('Car deleted successfully');
          this.cars = this.cars.filter(car => car.carId !== this.selectedCar.carId);
          this.closeModal();
        },
        (error) => {
          console.error('Error deleting car:', error);
        }
      );
    }
  }
}
