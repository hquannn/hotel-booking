import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HotelRoom } from '../_model/hotemRoom';

@Injectable({
  providedIn: 'root'
})
export class HotelRoomService {
  readonly apiUrl = "http://localhost:8080/API/";
  constructor(private http: HttpClient) { }

  getAll(request: string, hotelId: number): Observable<any>{
    let _url = this.apiUrl + "RoomHotel/getSearched?";
    if (request !== null || request !== undefined) {
      _url += "request=" + encodeURIComponent("" + request) + "&";
    }
    if (hotelId !== null || hotelId !== undefined) {
      _url += "hotelID=" + encodeURIComponent("" + hotelId) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.get<any>(_url);
  }

  createOrUpdate(data: HotelRoom){
    let _url = this.apiUrl + "RoomHotel";
    if(data.id > 0){
      return this.http.put(_url, data); 
    }
    return this.http.post(_url, data); 
  }

  delete(Id: number){
    let _url = this.apiUrl + "RoomHotel?";
    if (Id !== null || Id !== undefined) {
      _url += "id=" + encodeURIComponent("" + Id) + "&";
    }
    _url = _url.replace(/[?&]$/, "");
    return this.http.delete(_url);
  }
}
