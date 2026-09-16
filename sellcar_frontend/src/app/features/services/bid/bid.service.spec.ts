import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { BidService } from './bid.service';

describe('BidService', () => {
  let service: BidService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });
    service = TestBed.inject(BidService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
