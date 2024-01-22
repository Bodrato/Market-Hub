import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../api.service';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  providers: [ApiService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private apiService: ApiService, public router: Router) { }
  errorMessage: string = '';

  registerForm = new FormGroup({
    nickname: new FormControl('',[Validators.required]),
    realName: new FormControl('',Validators.required),
    address: new FormControl('',Validators.required),
    email: new FormControl('',[Validators.required,Validators.email]),
    phone: new FormControl('',[Validators.required]),
    password: new FormControl('',Validators.required)
  });

  register() {
    if(this.registerForm.invalid) return;
    
    const account = this.registerForm.value as Record<string, string>;

    this.apiService.saveAccount(account).subscribe({
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
            default:
              this.errorMessage = 'Error desconocido.';
              break;
          }
      }
    });

  }

  get nickname() {
    return this.registerForm.controls.nickname
  }

  get realName() {
    return this.registerForm.controls.realName
  }

  get address() {
    return this.registerForm.controls.address
  }

  get email() {
    return this.registerForm.controls.email
  }

  get phone() {
    return this.registerForm.controls.phone
  }

  get password() {
    return this.registerForm.controls.password
  }
}




