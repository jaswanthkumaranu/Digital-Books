import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-book-content',
  templateUrl: './book-content.component.html',
  styleUrls: ['./book-content.component.css']
})
export class BookContentComponent implements OnInit {

  content?:string;
  constructor(private router: Router) { }

  ngOnInit(): void {
     console.log(this.router.getCurrentNavigation);
      

  }

}
