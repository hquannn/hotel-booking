import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { Account } from '../_model/account';
import {map} from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AccountService {
  readonly apiUrl = 'http://localhost:8080/API/';
  private currentUser = new ReplaySubject<any>(1);
  currentUser$ = this.currentUser.asObservable()
  constructor(private http: HttpClient) { } 

  login(email: string, password : string){
    let _url = this.apiUrl + "Account/login?email=" + encodeURIComponent("" + email) + "&password=" + encodeURIComponent("" + password);
    return this.http.get<any>(_url)
  }

  setCurrentUser(user: any){
    this.currentUser.next(user);
  }

  logOut(){
    localStorage.removeItem('user');
    this.currentUser.next(null);
  }

  registerAccount(account: Account){
    let _url = this.apiUrl + "Account/create";
    return this.http.post(_url, account); 
  }
  
  getById(id: number){
    let _url = this.apiUrl + "Account/GetById?id=" + encodeURIComponent("" + id);
    return this.http.get<any>(_url); 
  }

  updatePassword(email: string, newPass: string, oldPass: string){
    let _url = this.apiUrl + "Account/changePassword?";
    if (email !== null || email !== undefined) {
      _url += "email=" + encodeURIComponent("" + email) + "&";
    }
    if (newPass !== null || newPass !== undefined) {
      _url += "newPass=" + encodeURIComponent("" + newPass) + "&";
    }
    if (oldPass !== null || oldPass !== undefined) {
      _url += "oldPass=" + encodeURIComponent("" + oldPass) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.get<any>(_url); 
  }

  updateAccount(val: any){
    let _url = this.apiUrl + "Account/updateInfo";
    return this.http.put(_url, val);
  }

  deleteAccount(id: number){
    let _url = this.apiUrl + "Account?id=" + encodeURIComponent("" + id);
    return this.http.delete(_url);
  }

  getAll(request: string, pageNumber: number) : Observable<{list: any[], total: any }>{
    let _url = this.apiUrl + "Account/getAll?";
    if (request !== null || request !== undefined) {
      _url += "request=" + encodeURIComponent("" + request) + "&";
    }
    if (pageNumber !== null || pageNumber !== undefined) {
      _url += "pageNumber=" + encodeURIComponent("" + pageNumber) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.get<any>(_url);
  }
}
