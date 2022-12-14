import { Component, OnInit } from '@angular/core';
import { Book } from '../book';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-reader',
  templateUrl: './board-reader.component.html',
  styleUrls: ['./board-reader.component.css']
})
export class BoardReaderComponent implements OnInit {
  bookList?: Book[] = [];
  email!: '';
  errorMessage = '';
  
  constructor(private userService: UserService,private token: TokenStorageService) { }

  ngOnInit(): void {
    this.email=this.token.getUser().email;
    console.log(this.email);
    
    this.userService.getReaderSubscribesBooks(this.email).subscribe(
      data => {
        this.bookList = JSON.parse(data);
        console.log(this.bookList);
        
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
  }

}
