import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from '../book';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-reader',
  templateUrl: './board-reader.component.html',
  styleUrls: ['./board-reader.component.css']
})
export class BoardReaderComponent implements OnInit {
  bookList?: Book[];
  email!: '';
  errorMessage = '';
  buttonValue='Cancel Subscription';
  
  constructor(private userService: UserService,private token: TokenStorageService,private router:Router) { }

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
  catchContent(bookContent:any){
    this.router.navigate(['/read',{bookContent:bookContent}])
  }
  cancelSubscribe(subscriptionId:string):void{
    console.log('subscriptionId:'+subscriptionId);
     
    this.userService.cancelSubscribe(subscriptionId).subscribe(
      data => {
        console.log(data);
        alert(JSON.parse(data).message);
       this.ngOnInit();
      },
      err => {
        this.errorMessage = err.error.message;

      }
    );

    

  }

}
