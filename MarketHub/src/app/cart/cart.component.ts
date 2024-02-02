import { AfterViewInit, Component, QueryList, ViewChildren } from '@angular/core';
import { ItemCartComponent } from '../item-cart/item-cart.component';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [ItemCartComponent, HeaderComponent, FooterComponent, CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements AfterViewInit{
  @ViewChildren(ItemCartComponent) itemCartComponents!: QueryList<ItemCartComponent>;


  cartItems: { idProduct: number }[] = [
    { idProduct: 2 },
    { idProduct: 3 },
  ];

  total: number = 0;

  ngAfterViewInit(): void {
    this.updateTotal();
  }

  updateTotal(): void {
    this.total = 0;
    this.itemCartComponents.forEach((itemCart) => {
      itemCart.totalChanged.subscribe((t) => {
        this.total = 0
        this.itemCartComponents.forEach((item) => {
          if(item.product != null)
            this.total += item.product.price * item.quantity ;
        });
      });
    });
  }

  onBuyClick(): void {
    console.log('Compra realizada');
  }
}
