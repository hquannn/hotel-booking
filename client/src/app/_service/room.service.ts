import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Room } from '../_model/room';

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  readonly apiUrl = "http://localhost:8080/API/";
  constructor(private http: HttpClient) { }

  getAll(request: string): Observable<any>{
    let _url = this.apiUrl + "Room/getSearchedRoom?";
    if (request !== null || request !== undefined) {
      _url += "request=" + encodeURIComponent("" + request) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.get<any>(_url);
  }

  createOrUpdate(data: Room){
    let _url = this.apiUrl + "Room";
    if(data.id > 0){
      return this.http.put(_url, data); 
    }
    return this.http.post(_url, data); 
  }

  delete(Id: number){
    let _url = this.apiUrl + "Room?";
    if (Id !== null || Id !== undefined) {
      _url += "id=" + encodeURIComponent("" + Id) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.delete(_url);
  }
}
