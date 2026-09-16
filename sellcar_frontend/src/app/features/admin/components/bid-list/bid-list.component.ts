import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
interface Bid {
  bidId: number;
  price: number;
  userId: number;
  carId: number;
  bidStatus: string;
  carName?: string;
  username?: string;
  userEmail?: string;
  carPrice?: number;
}
@Component({
  selector: 'app-bid-list',
  standalone: false,
  
  templateUrl: './bid-list.component.html',
  styleUrl: './bid-list.component.css'
})
export class BidListComponent {
  bids: Bid[] = [];
  isLoading = true;
  statusOptions: string[] = ['PENDING', 'APPROVED', 'REJECTED'];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.fetchBids();
  }

  fetchBids(): void {
    this.adminService.getAllBids().subscribe(
      (bids: Bid[]) => {
        this.bids = bids;
        this.isLoading = false;
      },
      (error) => {
        console.error('Error fetching bids:', error);
        this.isLoading = false;
      }
    );
  }
  updateBidStatus(bidId: number, newStatus: string): void {
    console.log("Update",bidId, newStatus);
    this.adminService.updateBidStatus(bidId, newStatus).subscribe(
      (updatedBid) => {
        if (!updatedBid) {
          console.error("Failed to update bid: No response from server.");
          return;
        }
        this.bids = this.bids.map(bid =>
          bid.bidId === updatedBid.bidId ? { ...bid, bidStatus: updatedBid.bidStatus } : bid
        );
      },
      (error) => {
        alert("Error updating bid: " + error.message);
      }
    );
  }
  
  
}
