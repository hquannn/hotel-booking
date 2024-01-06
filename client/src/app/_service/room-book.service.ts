import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoomBook } from '../_model/room_book';

@Injectable({
  providedIn: 'root'
})
export class RoomBookService {
  readonly apiUrl = "http://localhost:8080/API/";
  constructor(private http: HttpClient) { }

  getAll(request: string): Observable<any>{
    let _url = this.apiUrl + "RoomBook?";
    if (request !== null || request !== undefined) {
      _url += "request=" + encodeURIComponent("" + request) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.get<any>(_url);
  }

  updateStatus(data: RoomBook){
    let _url = this.apiUrl + "RoomBook";
    return this.http.put(_url, data); 
  }
  create(data: RoomBook){
    let _url = this.apiUrl + "RoomBook";
    return this.http.post(_url, data); 
  }
} 
