import { Component, OnInit } from '@angular/core';
import { HotelService } from '../_service/hotel.service';
import { Hotel } from '../_model/hotel';
import { Router } from '@angular/router';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {
  request: string = "";
  listHotelData: Hotel[] | undefined;
  constructor(private _service: HotelService,private router: Router) { }
  ngOnInit(): void {
    this.getAllRoom()
  }

  getAllRoom() {
    this._service.getAll(this.request).subscribe((res: any) => {
      console.log(res);
      this.listHotelData = res;
    })
  }

  search(event?: any){
    if(event.keyCode === 13){
      this.getAllRoom();
    }
  }

  click(data:any){
    this.router.navigateByUrl('Hotel/' + data.id);
  }


}
