import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from '../book';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css']
})
export class UpdateBookComponent implements OnInit {
  bookList?: Book[] = [];
  errorMessage = '';
  buttonValue = 'Edit Book';
  authorId: string='';
  blockValue:string='Block/UnBlock';
  constructor(private userService: UserService, private token: TokenStorageService,private router:Router) { }

  ngOnInit(): void {
    this.authorId = this.token.getUser().id;
    this.userService.getAuthorCreatedBooks(this.authorId).subscribe(
      data => {
        this.bookList = JSON.parse(data);
        console.log(this.bookList);

      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
  }

  updateBook(bookId: string): void {
    console.log(bookId);
    this.router.navigate(['/editBook',{bookId:bookId}])

  }
  blockBook(bookId: string,block:string): void {
    this.authorId=this.token.getUser().id;
    this.userService.blockOrUnblockBook(this.authorId,bookId,block).subscribe(
      data => {
        alert(data.message);
        console.log(block);
        
        console.log(data);
        this.ngOnInit();
        
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );

  }

}
