import { Component, OnInit, Renderer2, ElementRef } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { Account } from '../model/Account';
import { ApiService } from '../api.service';
import { Product } from '../model/Product';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-main',
  standalone: true,
  imports: [HeaderComponent,FooterComponent, HttpClientModule],
  providers: [ApiService],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {
  products: Product[] = []
  errorMessage: string = '';


  constructor(private renderer: Renderer2, private el: ElementRef, private apiService: ApiService) {}


  ngOnInit() {
    this.loadProducts();
    console.log(this.products.length)
    const account: Account = JSON.parse(localStorage.getItem('Account') || '{}');
  }

  generateProducts(){
    const productContainer = this.el.nativeElement.querySelector('#productContainer');

        if (productContainer) {
          this.products.forEach(product => {
            const productCard = this.renderer.createElement('div');
            this.renderer.addClass(productCard, 'product-card');
    
            const productImage = this.renderer.createElement('img');
            this.renderer.setAttribute(productImage, 'src', "https://cdn.pixabay.com/photo/2023/11/16/10/26/casio-8392121_1280.jpg");
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

  loadProducts() {
    this.apiService.getProduts().subscribe({
      next: (data: Product[]) => { 
        console.log(data)
        this.products = data
       },
       complete:() => {
        this.generateProducts()
       },
      error: (e: { status: any; }) => { 
          switch (e.status) {
            case 400:
              this.errorMessage = 'Error 400: La solicitud es inválida.';
              break;
            case 500:
              this.errorMessage = 'Error 500: Error interno del servidor.';
              break;
            case 404:
             this.errorMessage = 'No existe el usuario con ese email o contraseña.';
            break;
            default:
              this.errorMessage = 'Error desconocido.';
              break;
          }
      }
    });

  }
}