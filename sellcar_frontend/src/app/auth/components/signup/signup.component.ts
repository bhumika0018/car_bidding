import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-signup',
  standalone: false,
  
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  signupData = {
    name: '',
    email: '',
    password: ''
  };

  constructor(private storageService: StorageService,private router:Router) {}

  onSignup(): void {
    this.storageService.signup(this.signupData).subscribe(
      (response) => {
        console.log("Sign");
        alert('Signup successful!');
        this.router.navigate(['/login']);
      },
      (error) => {
        alert('Signup failed: ' + error.error);
        
      }
    );
  }
  

}


