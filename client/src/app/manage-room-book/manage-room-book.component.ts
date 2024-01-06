import { Component, OnInit } from '@angular/core';
import { RoomBookService } from '../_service/room-book.service';
import { RoomBook } from '../_model/room_book';
import { RoomBookData } from '../_model/room_book_data';
declare var $:any;
@Component({
  selector: 'app-manage-room-book',
  templateUrl: './manage-room-book.component.html',
  styleUrls: ['./manage-room-book.component.css']
})
export class ManageRoomBookComponent implements OnInit {
  request: string = "";
  listData: RoomBookData[] | undefined;
  data: RoomBook = {
    id: 0,
    hoteRoomId: 0,
    checkIn: "",
    checkOut: "",
    status: "",
    fullName: "",
    phone: "",
    description: "",
    creationTime: "",
    totalPrice:0,
    accountId: 0,
  };

  constructor(private _service: RoomBookService){}
  ngOnInit(): void {
    this.getAll();
  }

  getAll(){
    this._service.getAll(this.request).subscribe((res)=>{
      this.listData = res;
      if(this.listData)
      this.listData.forEach(result => {
        result.checkIn = new Date(result.checkIn).toLocaleDateString()
        result.checkOut = new Date(result.checkOut).toLocaleDateString()
      })
      
    })
  }

  search(event?: any) {
    if (event) {
      if (event.keyCode === 13) { 
        this.getAll();
      }
    } else {
      this.getAll();
    }
  }

  
  updateStatus(id:number,check:boolean) {
    this.data = {
      id: id,
      hoteRoomId: 0,
      checkIn: "",
      checkOut: "",
      status: "",
      fullName: "",
      phone: "",
      description: "",
      creationTime: "",
      totalPrice:0,
      accountId: 0
    };
    if(check){
      this.data.status ="Đã xác nhận"
    }
    else{
      if(!confirm("Bạn có chắc muốn hủy đơn đặt phòng này?")) return;
      this.data.status ="Từ chối"
    }
    
    this._service.updateStatus(this.data).subscribe(res => {
      alert("Thành công!");
      this.getAll();
    }, error => {
      alert("Đã xảy ra lỗi gì đó! Hãy kiểm tra lại đường truyền!")
    })
  }

  
}
