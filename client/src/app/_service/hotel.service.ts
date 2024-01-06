import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Hotel } from '../_model/hotel';

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  readonly apiUrl = "http://localhost:8080/API/";
  constructor(private http: HttpClient) { }

  getAll(request: string): Observable<any>{
    let _url = this.apiUrl + "Hotel/getAll?";
    if (request !== null || request !== undefined) {
      _url += "request=" + encodeURIComponent("" + request) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.get<any>(_url);
  }

  getById(Id: number): Observable<Hotel>{
    let _url = this.apiUrl + "Hotel/getById?";
    if (Id !== null || Id !== undefined) {
      _url += "id=" + encodeURIComponent("" + Id) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.get<Hotel>(_url);
  }

  createOrUpdate(data: Hotel){
    let _url = this.apiUrl + "Hotel";
    if(data.id > 0){
      return this.http.put(_url, data); 
    }
    return this.http.post(_url, data); 
  }

  delete(Id: number){
    let _url = this.apiUrl + "Hotel?";
    if (Id !== null || Id !== undefined) {
      _url += "id=" + encodeURIComponent("" + Id) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.delete(_url);
  }

}
