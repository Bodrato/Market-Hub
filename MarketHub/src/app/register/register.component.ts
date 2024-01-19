import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm = new FormGroup({
    nickname: new FormControl('',[Validators.required]),
    realName: new FormControl('',Validators.required),
    address: new FormControl('',Validators.required),
    email: new FormControl('',[Validators.required,Validators.email]),
    phone: new FormControl('',[Validators.required,Validators.pattern("[0-9 ]{11}")]),
    password: new FormControl('',Validators.required)
  });

  register() {
    if(this.registerForm.invalid) return;
    alert('hola');
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




