import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { BidListComponent } from './bid-list.component';

describe('BidListComponent', () => {
  let component: BidListComponent;
  let fixture: ComponentFixture<BidListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BidListComponent],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BidListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
