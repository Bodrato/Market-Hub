import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  providers: [ApiService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  errorMessage: string = '';

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  constructor(public router: Router, private apiService: ApiService) { }

  login(){
    if(this.loginForm.invalid) return;

    const loginObject: Record<string, string> = this.loginForm.value as Record<string, string>;

    this.apiService.getAccount(loginObject).subscribe({
      //TODO
      next: (data) => {  },
      complete: () => { this.router.navigate(['home']) },
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

  get email()
  {
    return this.loginForm.controls.email
  }

  get password()
  {
    return this.loginForm.controls.password
  }
}

