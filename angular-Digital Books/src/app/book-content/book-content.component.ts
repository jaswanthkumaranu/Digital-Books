import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-book-content',
  templateUrl: './book-content.component.html',
  styleUrls: ['./book-content.component.css']
})
export class BookContentComponent implements OnInit {

  content?:string |null='';
  constructor(private route:ActivatedRoute) { }

  ngOnInit(): void {
     this.content= this.route.snapshot.paramMap.get('bookContent');
      

  }

}
