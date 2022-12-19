import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';
import { ReaderVo } from '../reader-vo';
import { AppConfig } from '../_helpers/app.config';

const API_URL = 'http://localhost:8082/api/user/';
// const API_URL=AppConfig.getAPIURI+'user/';
const AWS_AUTH_API='https://obof01oudj.execute-api.us-east-1.amazonaws.com/UAT-user/';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }
  getReaderSubscribesBooks(email: string): Observable<any> {
    // return this.http.get(API_URL + `readers/${email}/books`, { responseType: 'text' });
    return this.http.get(AWS_AUTH_API+'signin/'+email, { responseType: 'text' });
  }
  getAuthorCreatedBooks(authorId: string ): Observable<any> {
    // return this.http.get(API_URL + 'author/' + authorId + '/books', { responseType: 'text' });
    return this.http.get(AWS_AUTH_API +  authorId , { responseType: 'text' });
  }
  getAuthorCreatedBook(id: string | null, bookId: string | null): Observable<any> {
    // return this.http.get(API_URL + 'author/' + id + '/books/'+bookId, { responseType: 'text' });
    return this.http.get(AWS_AUTH_API +  id + '/'+bookId, { responseType: 'text' });
  }
 
  blockOrUnblockBook(authorId: string ,bookId:string,block:string): Observable<any> {
    // return this.http.post(API_URL + 'author/' + authorId + '/books/'+bookId+'/block='+block, { responseType: 'text' });
    return this.http.post(AWS_AUTH_API +  authorId + '/'+bookId+'/'+block, { responseType: 'text' });
  }
  subscribeBook(reader:ReaderVo ,bookId: string): Observable<any>  {
    // return this.http.post(API_URL+bookId+'/subscribe', reader,{responseType: 'text'});
      return this.http.post(AWS_AUTH_API+'/user/'+bookId+'/subscribe', reader,{responseType: 'text'});
  }
  cancelSubscribe(subscriptionId: string) : Observable<any> {
    // return this.http.post(API_URL+'readers/'+ this.tokenStorage.getUser().id +'/books/'+subscriptionId+'/cancel-subscription', null,{responseType: 'text'});
    return this.http.post(AWS_AUTH_API+'signin/{emailId}/'+subscriptionId+'/'+ this.tokenStorage.getUser().id, null,{responseType: 'text'});
  }
  search(category: string, title: string, author: string, price: number, publisher: string): Observable<any> {
    
    // return this.http.get(API_URL + 'search?category=' + category + '&title=' + title + '&author=' + author + '&price=' + price + '&publisher=' + publisher,
    //   {
    //     responseType: 'text'
    //   });
      return this.http.get(AWS_AUTH_API + 'search?category=' + category + '&title=' + title + '&author=' + author + '&price=' + price + '&publisher=' + publisher,
      {
        responseType: 'text'
      });
      
  }
  create(create: any) {
    console.log(this.tokenStorage.getUser().id);
    // return this.http.post(API_URL + 'author/' + this.tokenStorage.getUser().id + '/books', create, {
    //   responseType: 'text'
    // });

    return this.http.post(AWS_AUTH_API + this.tokenStorage.getUser().id , create, {
      responseType: 'text'
    });
  }
  editBook(id: string | null, bookId: string | null, form: any)  : Observable<any>{
    // return this.http.post(API_URL + 'author/'+id+'/books/'+bookId, form, {
    //   responseType: 'text'
    // });

    return this.http.post(AWS_AUTH_API + this.tokenStorage.getUser().id+'/'+bookId , form, {
      responseType: 'text'
    });
  }
}
