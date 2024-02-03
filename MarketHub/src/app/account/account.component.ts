import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../api.service';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FooterComponent } from '../footer/footer.component';
import { Account } from '../model/Account';


@Component({
  selector: 'app-account',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule, FooterComponent],
  providers: [ApiService],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit {
  constructor(private apiService: ApiService, public router: Router) { }
  errorMessage: string = '';
  loggedInAccount: Account | null = null;

  accountForm = new FormGroup({
    nickname: new FormControl('', [Validators.required]),
    realName: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    phone: new FormControl('', [Validators.required]),
    password: new FormControl('', Validators.required)
  });

  ngOnInit() {
    const loggedInAccountString = localStorage.getItem('Account');
    if (loggedInAccountString) {
      this.loggedInAccount = JSON.parse(loggedInAccountString);
      this.accountForm.patchValue({
        nickname: this.loggedInAccount?.nickname,
        realName: this.loggedInAccount?.realName,
        address: this.loggedInAccount?.address,
        email: this.loggedInAccount?.email,
        phone: this.loggedInAccount?.phone,
        password: this.loggedInAccount?.password
      });
    }
  }

  updateAccount() {
    console.log('loggedInAccount:', this.loggedInAccount);
    console.log('La función se está ejecutando.');
    if (this.accountForm.invalid || !this.loggedInAccount) return;

    const updatedAccount = { ...this.accountForm.value, idAccount: this.loggedInAccount?.idAccount } as Account;

    this.apiService.updateAccount(updatedAccount).subscribe({
      next: (data) => { },
      complete: () => { this.router.navigate(['home']); },
      error: (e) => {
        console.error('Error updating account:', e);
        switch (e.status) {
          case 400:
            this.errorMessage = 'Error 400: La solicitud es inválida.';
            break;
          case 500:
            this.errorMessage = 'Error 500: Error interno del servidor.';
            break;
          default:
            this.errorMessage = 'Error desconocido.';
            break;
        }
      }
    });
  }

  get nickname() {
    return this.accountForm.controls.nickname;
  }

  get realName() {
    return this.accountForm.controls.realName;
  }

  get address() {
    return this.accountForm.controls.address;
  }

  get email() {
    return this.accountForm.controls.email;
  }

  get phone() {
    return this.accountForm.controls.phone;
  }

  get password() {
    return this.accountForm.controls.password;
  }
}
