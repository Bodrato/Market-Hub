import { Component, OnInit, Renderer2, ElementRef } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';


@Component({
  selector: 'app-main',
  standalone: true,
  imports: [HeaderComponent,FooterComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {
  products: any[] = [
    {
      "name": "Sample Product 1",
      "description": "This is an example product",
      "price": 49.99,
      "image": "https://picsum.photos/512/512",
      "account": {
        "idAccount": 1
      }
    },
    {
      "name": "Sample Product 2",
      "description": "Another example product",
      "price": 29.99,
      "image": "https://picsum.photos/512/512",
      "account": {
        "idAccount": 2
      }
    },
    // Add more products as needed
  ];
  constructor(private renderer: Renderer2, private el: ElementRef) {}


  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    const productContainer = this.el.nativeElement.querySelector('#productContainer');

    if (productContainer) {
      this.products.forEach(product => {
        const productCard = this.renderer.createElement('div');
        this.renderer.addClass(productCard, 'product-card');

        const productImage = this.renderer.createElement('img');
        this.renderer.setAttribute(productImage, 'src', product.image);
        this.renderer.setAttribute(productImage, 'alt', product.name);

        const productDetails = this.renderer.createElement('div');
        this.renderer.addClass(productDetails, 'product-details');

        const productName = this.renderer.createElement('h3');
        this.renderer.setProperty(productName, 'textContent', product.name);

        const productPrice = this.renderer.createElement('h2');
        this.renderer.setProperty(productPrice, 'textContent', product.price + "€");

        this.renderer.appendChild(productDetails, productName);
        this.renderer.appendChild(productDetails,productPrice);
        this.renderer.appendChild(productCard, productImage);
        this.renderer.appendChild(productCard, productDetails);
        this.renderer.appendChild(productContainer, productCard);
      });
    }
  }
}