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
  public getProductById(id: number): Observable<Product> {
    const url = `${this.apiUrl}/products/${id}`;
    return this.http.get(url) as Observable<Product>;
  }

  // GET ACCOUNT BY ID
  public getAccountById(id: number | undefined): Observable<Account> {
    const url = `${this.apiUrl}/accounts/${id}`;
    return this.http.get(url) as Observable<Account>;
  }

  // PUT ACCOUNT (Update)
  public updateAccount(account: Account): Observable<Account> {
    const url = `${this.apiUrl}/accounts/${account.idAccount}`; 
    return this.http.put(url, account) as Observable<Account>;
  }
  
}
