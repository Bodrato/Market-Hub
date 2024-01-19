import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  constructor(public router: Router)
  {

  }

  get email()
  {
    return this.loginForm.controls.email
  }

  get password()
  {
    return this.loginForm.controls.password
  }
  login(){
    if(this.loginForm.invalid){
      return;
    }
    alert("ESTOY VIVO");
    this.router.navigate(['home'])
  }
}

