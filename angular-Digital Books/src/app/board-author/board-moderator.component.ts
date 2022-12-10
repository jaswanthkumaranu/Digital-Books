import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-moderator.component.html',
  styleUrls: ['./board-moderator.component.css']
})
export class BoardModeratorComponent implements OnInit {
  content?: string;
  create: any = {
    bookTitle: null,
    publisher: null,
    category:null,
    price:[(0)],
    isActive:null,
    bookContent:null
  };
  errorMessage = '';
  isCreatedIn = false;
  isCreatedFailed = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getModeratorBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  onSubmit(): void {
    const {  bookTitle,category, price,publisher,isActive,bookContent } = this.create;
    this.userService.create(this.create).subscribe(
      data => {
        console.log(data);

        this.isCreatedIn = true;
        this.isCreatedFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isCreatedFailed = true;
        
      }
    );

  }
}
