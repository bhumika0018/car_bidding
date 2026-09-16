import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private baseUrlCustomer = environment.customerApiUrl;
  private baseUrlBid = environment.bidApiUrl;

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwt');
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : '',
      'Content-Type': 'application/json',
    });
  }

  // Customer APIs
  // Get all available cars
  getAllCarsCustomer(): Observable<any> {
    return this.http.get(`${this.baseUrlCustomer}/cars`, {
      headers: this.getAuthHeaders(),
    });
  }

  // Get a car by ID (Customer)
  getCarByIdCustomer(carId: number): Observable<any> {
    return this.http.get(`${this.baseUrlCustomer}/car/${carId}`, {
      headers: this.getAuthHeaders(),
    });
  }

  // Search for cars (Customer)
  searchCarCustomer(searchData: any): Observable<any> {
    return this.http.post(`${this.baseUrlCustomer}/car/search`, searchData, {
      headers: this.getAuthHeaders(),
    });
  }



  // Bid APIs
  placeBid(bidData: any): Observable<any> {
    return this.http.post(`${this.baseUrlBid}/place`, bidData, {
      headers: this.getAuthHeaders(),
    });
  }

  getBidsByCar(carId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrlBid}/car/${carId}`, {
      headers: this.getAuthHeaders(),
    });
  }

  getBidsByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrlBid}/user/${userId}`, {
      headers: this.getAuthHeaders(),
    });
  }

  getHighestBidForCar(carId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrlBid}/car/${carId}/highest`, {
      headers: this.getAuthHeaders(),
    });
  }

  getBidCountForCar(carId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrlBid}/car/${carId}/count`, {
      headers: this.getAuthHeaders(),
    });
  }


  deleteBid(bidId: number): Observable<string> {
    return this.http.delete<string>(`${this.baseUrlBid}/delete/${bidId}`, {
      headers: this.getAuthHeaders(),
    });
  }
}
