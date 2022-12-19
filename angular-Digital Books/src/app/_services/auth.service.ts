import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConfig } from '../_helpers/app.config';

const AUTH_API = 'http://localhost:8082/api/auth/';
// const AUTH_API=AppConfig.getAPIURI+'auth';
const AWS_AUTH_API='https://obof01oudj.execute-api.us-east-1.amazonaws.com/UAT-user/';



const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
  
    // return this.http.post(AUTH_API + 'signin' , {
    return this.http.post(AWS_AUTH_API+'signin' , {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string, password: string,role:Array<String>): Observable<any> {
// return this.http.post(AUTH_API + 'signup' , {
    return this.http.post(AWS_AUTH_API , {
      username,
      email,
      password,
      role
    }, httpOptions);
  }
}
