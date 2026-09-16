import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-post-cars',
  standalone: false,
  templateUrl: './post-cars.component.html',
  styleUrl: './post-cars.component.css'
})
export class PostCarsComponent {

listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA", "HAVAL"]; 
listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
listOfColor = ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
listOfTransmission = ["Semi-Automatic", "Automatic"];
postCarForm: FormGroup;

constructor(private service: AdminService,private fb: FormBuilder,private router: Router){
  this.postCarForm = this.fb.group({
    brand: ['', Validators.required],
    name: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9\s]+$/)]],
    type: ['', Validators.required],
    transmission: ['', Validators.required],
    color: ['', Validators.required],
    year: ['', [Validators.required, Validators.min(1990), Validators.max(2025)]],
    price: ['', [Validators.required, Validators.min(1000)]],
    description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(500)]]
  });
}
ngOnInit() {
  
};

postCar(){
  if (this.postCarForm.invalid) {
    alert('Please correct the errors before submitting.');
    return;
  }
  
  console.log('Car Posted:', this.postCarForm.value);
  alert('Car posted successfully!');
  this.service.addCar(this.postCarForm.value).subscribe(
    (response) => {
      console.log('Car added successfully:', response);
      this.router.navigate(['/admin-dashboard']);
    },

    error => {
      console.error('Error posting car:', error);
      alert('Error posting car. Please try again later.');
    }
  );
  // this.postCarForm.reset();
}
}
