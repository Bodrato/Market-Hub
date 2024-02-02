import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../model/Product';
import { ApiService } from '../api.service';
import { HttpClientModule } from '@angular/common/http';
import { Account } from '../model/Account';

@Component({
  selector: 'app-item-view',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, HttpClientModule],
  providers: [ApiService],
  templateUrl: './item-view.component.html',
  styleUrl: './item-view.component.css'
})
export class ItemViewComponent implements OnInit {
  productId: number = -1;
  product: Product | undefined;
  sellerAccount: Account | undefined;
  errorMessage: string = '';

  constructor(private route: ActivatedRoute, private apiService: ApiService) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.productId = params.get('id') as unknown as number;
    });

    this.getProduct();
  }

  getProduct() {
    this.apiService.getProductById(this.productId).subscribe({
      next: (data: Product) => {
        this.product = data;
      },
      error: (e) => {
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

  addToCart() {
    localStorage.setItem('cart', localStorage.getItem('cart') + ',' + this.product?.idProduct);
    alert(localStorage.getItem('cart'));
  }
}
