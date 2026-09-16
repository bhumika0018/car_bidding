import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-user-list',
  standalone: false,
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit {
  customers: any[] = [];  // Original customer list
  filteredCustomers: any[] = []; // Filtered customer list
  loading: boolean = true;
  error: boolean = false;
  searchUsername: string = ''; // Search term

  constructor(private http: HttpClient, private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadCustomers();
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwt');
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json'
    });
  }

  loadCustomers(): void {
    this.adminService.getCustomers().subscribe(
      (data) => {
        this.customers = data || [];  
        this.filteredCustomers = [...this.customers]; // Initialize filtered list
        this.loading = false;
      },
      (error) => {
        this.error = true;
        this.loading = false;
      }
    );
  }

  searchUser(): void {
    const searchTerm = this.searchUsername.trim().toLowerCase();
    if (searchTerm) {
      this.filteredCustomers = this.customers.filter(customer =>
        customer.name.toLowerCase().includes(searchTerm)
      );
    } else {
      this.filteredCustomers = [...this.customers]; // Reset when empty
    }
  }

  deleteUser(userId: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.adminService.deleteUser(userId).subscribe(
        (response) => {
          this.customers = this.customers.filter(user => user.id !== userId);
          this.filteredCustomers = [...this.customers]; // Update filtered list
        },
        (error) => {
          console.error('Error deleting user', error);
          alert('Failed to delete user.');
        }
      );
    }
  }
}
