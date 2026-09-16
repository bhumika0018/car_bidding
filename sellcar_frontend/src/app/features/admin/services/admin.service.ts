import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
}) 
export class AdminService {
  private baseUrlAuth = environment.authApiUrl;
  private baseUrlAdmin = environment.adminApiUrl;
  private baseUrlBid = environment.bidApiUrl;

    private getAuthHeaders(): HttpHeaders {
      const token = localStorage.getItem('jwt');
      return new HttpHeaders({
        'Authorization': token ? `Bearer ${token}` : '',
        'Content-Type': 'application/json'
      });
    }
    constructor(private http: HttpClient) {}

    // Get all customers
    getCustomers(): Observable<any[]> {
      return this.http.get<any[]>(`${this.baseUrlAuth}/customers`, { headers: this.getAuthHeaders() });
    }
    
    // Delete a user
    deleteUser(userId: number): Observable<any> {
      return this.http.delete(`${this.baseUrlAuth}/${userId}/delete`, { headers: this.getAuthHeaders() });
    }
    getProfile(username:string): Observable<any> {
      return this.http.get(`${this.baseUrlAuth}/${username}`, { headers: this.getAuthHeaders() });
    }

    // Admin APIs
    // Add a new car
    addCar(carData: FormData): Observable<any> {
      console.log("cardata", carData);
      return this.http.post(`${this.baseUrlAdmin}/car`, carData, {headers: this.getAuthHeaders()});
    }
  
    // Get all cars
    getAllCarsAdmin(): Observable<any> {
      return this.http.get(`${this.baseUrlAdmin}/cars`, { headers: this.getAuthHeaders() });
    }
    
  
    // Get a car by ID
    getCarByIdAdmin(carId: number): Observable<any> {
      return this.http.get(`${this.baseUrlAdmin}/car/${carId}`, { headers: this.getAuthHeaders() });
    }
  
    // Delete a car
    deleteCar(carId: number): Observable<any> {
      return this.http.delete(`${this.baseUrlAdmin}/car/${carId}`, { headers: this.getAuthHeaders() });
    }
  
    // Update car details
    updateCar(carId: number, carData: FormData): Observable<any> {
      return this.http.put(`${this.baseUrlAdmin}/car/${carId}`, carData, { headers: this.getAuthHeaders() });
    }
  
    // Search for cars (Admin)
    searchCarAdmin(searchData: any): Observable<any> {
      return this.http.post(`${this.baseUrlAdmin}/car/search`, searchData, {headers: this.getAuthHeaders()});
    }


    // Bid APIs
    getAllBids(): Observable<any[]> {
      return this.http.get<any[]>(`${this.baseUrlBid}/all`, {
        headers: this.getAuthHeaders(),
      });
    }

    updateBidStatus(bidId: number, status: string): Observable<any> {
      console.log(status);
      return this.http.put(`${this.baseUrlBid}/update/${bidId}`, {status},{headers: this.getAuthHeaders()});
    }
}
