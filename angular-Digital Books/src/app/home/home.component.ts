import { Component, OnInit } from '@angular/core';
import { Book } from '../book';
import { ReaderVo } from '../reader-vo';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {
  content?: string;
  errorMessage = '';
  role = this.token.getUser()!=null&&this.token.getUser().roles!=null?this.token.getUser().roles[0]:'';

  form: any = {
    title: null,
    author: null,
    publisher: null,
    category: null,
    price: [(0)]
  };
 
  bookList?: Book[] = [];
 reader:ReaderVo={
   readerId: '',
   readerEmail: ''
 };

  constructor(private userService: UserService, private token: TokenStorageService) { }

  ngOnInit(): void {
    // this.userService.getPublicContent().subscribe(
    //   data => {
    //     console.log('$$$content$$$' + data);

    //     this.content = data;
    //   },
    //   err => {
    //     this.content = JSON.parse(err.error).message;

    //   }
    // );
  }
  subscribe(bookId:string):void{
    
    this.reader.readerId=this.token.getUser().id;
    this.reader.readerEmail=this.token.getUser().email;
    this.userService.subscribeBook(this.reader,bookId).subscribe(
      data => {
        console.log(data);
        alert(JSON.parse(data).message);
        
      },
      err => {
        this.errorMessage = err.error.message;

      }

    );
    
  }
  onSubmit(): void {
    const { category, title, author, price, publisher } = this.form;
    this.userService.search(category, title, author, price, publisher).subscribe(
      data => {
        this.bookList = JSON.parse(data);
      },
      err => {
        this.errorMessage = err.error.message;

      }
    );

  }
 
}
