import { AfterViewInit, Component, OnInit, QueryList, ViewChildren } from '@angular/core';
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
export class CartComponent implements AfterViewInit, OnInit{

  @ViewChildren(ItemCartComponent) itemCartComponents!: QueryList<ItemCartComponent>;


  cartItems: { idProduct: number, quantity:number }[] = [];

  total: number = 0;

  ngOnInit(): void {
    console.log(this.getCarts())
    this.cartItems = this.getCarts()
  }

  ngAfterViewInit(): void {
    this.updateTotal();
  }

  updateTotal(): void {
    this.total = 0;
    this.itemCartComponents.forEach((itemCart) => {
      itemCart.totalChanged.subscribe((t) => {
        this.total = 0
        this.itemCartComponents.forEach((item) => {
          if(item.quantity == undefined)
            item.quantity = 1
          if(item.product != null)
            this.total += item.product.price * item.quantity ;
        });
      });
    });
  }

  onBuyClick(): void {
    console.log('Compra realizada');
  }

  getCarts() {
    let cartItems: { idProduct: number, quantity: number }[] = [];
    const idsString = localStorage.getItem('cart');
  
    if (idsString) {
      const idsArray = idsString.split(',');
  
      for (let idString of idsArray) {
        const idNumber = Number.parseInt(idString);
  
        if (!isNaN(idNumber)) {
          const existingItem = cartItems.find(item => item.idProduct === idNumber);
  
          if (existingItem) {
            existingItem.quantity += 1;
          } else {
            cartItems.push({ idProduct: idNumber, quantity: 1 });
          }
        }
      }
    }
  
    return cartItems;
  }
  
  
}
