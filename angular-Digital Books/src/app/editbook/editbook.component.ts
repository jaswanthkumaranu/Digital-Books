import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Book } from '../book';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-editbook',
  templateUrl: './editbook.component.html',
  styleUrls: ['./editbook.component.css']
})
export class EditbookComponent implements OnInit {

  form: any = {
    bookTitle: null,
    publisher: null,
    category: null,
    price: [(0)],
    isActive: null,
    bookContent: null
  };
  bookList?: Book[] = [];
  errorMessage = '';
  isCreatedIn = false;
  isCreatedFailed = false;
  bookId: string |null = '';
  id:string|null='';


  constructor(private route: ActivatedRoute,private userService: UserService,private token: TokenStorageService) { }
  
  ngOnInit(): void {
    this.id=this.token.getUser().id;
    this.bookId = this.route.snapshot.paramMap.get('bookId');
    this.userService.getAuthorCreatedBook(this.id,this.bookId).subscribe(
      data => {
        this.bookList = JSON.parse(data);
        console.log(this.bookList);
        if(this.bookList!=null&&this.bookList.length>0){
          this.form.bookTitle=this.bookList[0].bookTitle;
          this.form.category=this.bookList[0].category;
          this.form.publisher=this.bookList[0].publisher;
          this.form.price=this.bookList[0].price;
          this.form.isActive=this.bookList[0].isActive;
          this.form.bookContent=this.bookList[0].bookContent;
        }
        

      },
      err => {
        this.errorMessage = err.error.message;
      }
    );

  }

  onSubmit(): void {
    const {bookTitle, category, price, publisher, isActive, bookContent } = this.form;
    this.id=this.token.getUser().id;
    this.userService.editBook(this.id,this.bookId,this.form).subscribe(
      data => {
        console.log(data);
        if (data == 'Book with same title exists!') {
          this.errorMessage = data;
          this.isCreatedFailed = true;
        }
        else {
          this.isCreatedIn = true;
          this.isCreatedFailed = false;
        }
      },
      err => {
        this.errorMessage = err.error.message;
        this.isCreatedFailed = true;

      }
    );

  }

}
