import { Component, NgModule } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginData = {
    email: '',
    password: ''
  };

  constructor(private storageService: StorageService, private router: Router) {}

  onLogin(): void {
    this.storageService.login(this.loginData).subscribe(
      (response: any) => {
        // Save JWT and email in localStorage
        console.log(response)
        localStorage.setItem('jwt', response.jwt);
        localStorage.setItem('email', this.loginData.email);
        localStorage.setItem('userid', response.userId);
  
        alert('Login successful!');
  
        // Check if the email is 'admin@test.com'
        if (this.loginData.email === 'admin@test.com') {
          // Navigate to admin dashboard
          this.router.navigate(['/admin-dashboard']);
        } else {
          // Navigate to customer dashboard
          this.router.navigate(['/customer-dashboard']);
        }
      },
      (error) => {
        console.log(error);
        alert(`Login failed: ${error.name} \n${error.status}`);

      }
    );
  }
}
