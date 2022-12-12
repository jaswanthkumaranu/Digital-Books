import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  content?: string;
  errorMessage = '';

  form: any = {
    title: null,
    author: null,
    publisher: null,
    category:null,
    price:[(0)]
  };

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }
  onSubmit(): void {
    const { category, title, author,price,publisher } = this.form;
    this.userService.search(category, title, author,price,publisher).subscribe(
      data => {
        console.log(data);
      },
      err => {
        this.errorMessage = err.error.message;
        
      }
    );

  }
}
