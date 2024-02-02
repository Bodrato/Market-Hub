import { Account } from "./Account";

export interface Product {
    idProduct: number,
    description: string,
    image: string,
    name: string,
    price: number,
    account: Account
}