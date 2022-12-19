import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {
    username: null,
    email: null,
    password: null,
    role:[]=[],
  };

  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const { username, email, password,role } = this.form;
console.log(this.form);
    this.authService.register(username, email, password,role).subscribe(
      data => {
        if(data!=null&&data.message=='Error: Username is already taken!'){
          console.log(data);
          this.errorMessage = data.message;
          this.isSignUpFailed = true;
        }else{
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      }
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
