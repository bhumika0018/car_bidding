import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BidService {
  private baseUrl = environment.bidApiUrl;
  
  constructor(private http: HttpClient) {}

  getAllBids(): Observable<any> {
    return this.http.get(`${this.baseUrl}/all`);
  }

  getBidsByCar(carId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/car/${carId}`);
  }

  getBidsByUser(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/${userId}`);
  }

  placeBid(bidData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/place`, bidData);
  }

  updateBidStatus(bidId: number, status: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/update/${bidId}?status=${status}`, {});
  }

  deleteBid(bidId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${bidId}`);
  }

  getHighestBidForCar(carId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/car/${carId}/highest`);
  }

  getBidCountForCar(carId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/car/${carId}/count`);
  }
}
