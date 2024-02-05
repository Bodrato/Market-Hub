import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { HttpClientModule } from '@angular/common/http';
import { ApiService } from '../api.service';
import { Product } from '../model/Product';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-item-cart',
  standalone: true,
  imports: [HeaderComponent,FooterComponent, HttpClientModule, CommonModule, FormsModule],
  providers: [ApiService],
  templateUrl: './item-cart.component.html',
  styleUrl: './item-cart.component.css'
})
export class ItemCartComponent implements OnInit {
  @Input() idProduct: number | undefined;
  @Input() quantity: number | undefined
  @Output() totalChanged: EventEmitter<number> = new EventEmitter<number>();

  errorMessage: string = '';
  product: Product | undefined;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.fetchProduct();
  }



  fetchProduct(): void {
    if (this.idProduct != null) {
      this.apiService.getProductById(this.idProduct).subscribe({
        next: (data: Product) => {
          console.log(data);
          this.product = data;
        },
        complete: () => {
          this.updateTotal();
        },
        error: (e: { status: any }) => {
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
        },
      });
    }
  }

  updateTotal(): void {
    if (this.product != null) {
      if(this.quantity == undefined){
        this.quantity = 1
      }
      const total = this.product.price * this.quantity || 0;
      this.totalChanged.emit(total);
    }
  }

  onChangeQuantity(): void {
    this.updateTotal();
  }
}