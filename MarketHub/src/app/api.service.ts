import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Account } from './model/Account';
import { Product } from './model/Product';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8080/api'

  constructor(private http: HttpClient) { }

  // POST ACCOUNT
  public saveAccount(account: Account): Observable<Account> {
    const url = `${this.apiUrl}/accounts`;
    return this.http.post(url, account) as Observable<Account>;
  }

  // POST LOGIN
  public getAccount(account: Account): Observable<Account> {
    const url = `${this.apiUrl}/accounts/login`;
    return this.http.post(url, account) as Observable<Account>;
  }

  // GET PRODUCTS
  public getProduts(): Observable<Product[]> {
    const url = `${this.apiUrl}/products`;
    return this.http.get(url) as Observable<Product[]>;
  }

  // GET PRODUCT BY ID
  public getProductById(idProduct:number):Observable<Product> {
    const url =`${this.apiUrl}/products/${idProduct}`
    return this.http.get(url) as Observable<Product>;
  }
}
