import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8080/api'

  constructor(private http: HttpClient) { }

  // POST ACCOUNT
  public saveAccount(account: Record<string, string>): Observable<Record<string, string>> {
    const url = `${this.apiUrl}/accounts`;
    return this.http.post(url, account) as Observable<Record<string, string>>;
  }
}
