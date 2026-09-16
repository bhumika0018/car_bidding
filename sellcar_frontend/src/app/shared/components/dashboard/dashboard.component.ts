import { AfterViewInit, Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements AfterViewInit, OnDestroy {
  @ViewChild('scrollContainer', { static: false }) scrollContainer!: ElementRef;
  private autoScrollInterval: any;
  private userInteracted = false;

  // cars = [
  //   {
  //     name: 'Luxury Sedan',
  //     description: 'Experience comfort and style with this top-of-the-line sedan.',
  //     image: 'https://images.pexels.com/photos/112460/pexels-photo-112460.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500',
  //   },
  //   {
  //     name: 'Sporty Coupe',
  //     description: 'Unleash your adventurous side with this high-performance coupe.',
  //     image: 'https://images.pexels.com/photos/358070/pexels-photo-358070.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260',
  //   },
  //   {
  //     name: 'Rugged SUV',
  //     description: 'Conquer any terrain with this reliable and powerful SUV.',
  //     image: 'https://images.pexels.com/photos/693635/pexels-photo-693635.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500'
  //   },
  //   {
  //     name: 'Electric Hatchback',
  //     description: 'Go green with this efficient and stylish electric hatchback.',
  //     image: 'https://images.pexels.com/photos/100653/pexels-photo-100653.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500'
  //   },
  //   {
  //     name: 'Convertible Roadster',
  //     description: 'Feel the wind with this sleek and stylish convertible.',
  //     image: 'https://images.pexels.com/photos/358070/pexels-photo-358070.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260',
  //   },
  //   {
  //     name: 'Family Minivan',
  //     description: 'A spacious and comfortable ride for the whole family.',
  //     image: 'https://images.pexels.com/photos/244206/pexels-photo-244206.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500'
  //   }
  // ];
// cars = [
//   {
//     name: 'Luxury Sedan',
//     description: 'Experience comfort and style with this top-of-the-line sedan.',
//     image: 'https://images.unsplash.com/photo-1616789914094-3f3e9e3f9e2a?auto=format&fit=crop&w=800&q=80',
//   },
//   {
//     name: 'Sporty Coupe',
//     description: 'Unleash your adventurous side with this high-performance coupe.',
//     image: 'https://images.unsplash.com/photo-1617531653332-3e1c1f9c6f6e?auto=format&fit=crop&w=800&q=80',
//   },
//   {
//     name: 'Rugged SUV',
//     description: 'Conquer any terrain with this reliable and powerful SUV.',
//     image: 'https://images.unsplash.com/photo-1605559424843-7e3e1e3f9e2a?auto=format&fit=crop&w=800&q=80',
//   },
//   {
//     name: 'Electric Hatchback',
//     description: 'Go green with this efficient and stylish electric hatchback.',
//     image: 'https://images.unsplash.com/photo-1625863916325-3f3e9e3f9e2a?auto=format&fit=crop&w=800&q=80',
//   },
//   {
//     name: 'Convertible Roadster',
//     description: 'Feel the wind with this sleek and stylish convertible.',
//     image: 'https://images.unsplash.com/photo-1617531653332-3e1c1f9c6f6e?auto=format&fit=crop&w=800&q=80',
//   },
//   {
//     name: 'Family Minivan',
//     description: 'A spacious and comfortable ride for the whole family.',
//     image: 'https://images.unsplash.com/photo-1605559424843-7e3e1e3f9e2a?auto=format&fit=crop&w=800&q=80',
//   }
// ];

cars = [
  {
    name: 'Luxury Sedan',
    description: 'Experience comfort and style with this top-of-the-line sedan.',
    image: 'https://cdn.pixabay.com/photo/2016/11/29/04/17/audi-1868726_1280.jpg',
  },
  {
    name: 'Sporty Coupe',
    description: 'Unleash your adventurous side with this high-performance coupe.',
    image: 'https://cdn.pixabay.com/photo/2017/01/06/19/15/auto-1957037_1280.jpg',
  },
  {
    name: 'Rugged SUV',
    description: 'Conquer any terrain with this reliable and powerful SUV.',
    image: 'https://cdn.pixabay.com/photo/2020/05/03/17/46/jeep-5124890_1280.jpg',
  },
  {
    name: 'Electric Hatchback',
    description: 'Go green with this efficient and stylish electric hatchback.',
    image: 'https://cdn.pixabay.com/photo/2022/11/01/08/56/electric-car-7560142_1280.jpg',
  },
  {
    name: 'Convertible Roadster',
    description: 'Feel the wind with this sleek and stylish convertible.',
    image: 'https://cdn.pixabay.com/photo/2016/11/29/09/32/car-1868728_1280.jpg',
  },
  {
    name: 'Family Minivan',
    description: 'A spacious and comfortable ride for the whole family.',
    image: 'https://cdn.pixabay.com/photo/2017/03/27/14/56/car-2179220_1280.jpg',
  }
];
  ngAfterViewInit() {
    if (this.scrollContainer) {
      this.startAutoScroll();
    }
  }

  startAutoScroll() {
    this.autoScrollInterval = setInterval(() => {
      if (!this.userInteracted) {
        this.scrollRight();
      }
    }, 3000);
  }

  scrollLeft() {
    if (this.scrollContainer?.nativeElement) {
      this.userInteracted = true;
      this.scrollContainer.nativeElement.scrollBy({ left: -440, behavior: 'smooth' });
      this.resetAutoScroll();
    }
  }

  scrollRight() {
    if (this.scrollContainer?.nativeElement) {
      this.userInteracted = true;
      this.scrollContainer.nativeElement.scrollBy({ left: 440, behavior: 'smooth' });
      this.resetAutoScroll();
    }
  }

  resetAutoScroll() {
    clearInterval(this.autoScrollInterval);
    setTimeout(() => {
      this.userInteracted = false;
      this.startAutoScroll();
    }, 5000);
  }

  ngOnDestroy() {
    clearInterval(this.autoScrollInterval);
  }
}