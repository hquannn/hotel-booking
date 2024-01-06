import { Component, OnInit } from '@angular/core';
import { HotelRoom } from '../_model/hotemRoom';
import { ActivatedRoute } from '@angular/router';
import { HotelService } from '../_service/hotel.service';
import { HotelRoomService } from '../_service/hotel-room.service';
import { Hotel } from '../_model/hotel';
import { RoomData } from '../_model/roomData';
import { RoomBookData } from '../_model/room_book_data';
import { RoomBook } from '../_model/room_book';
import { RoomBookService } from '../_service/room-book.service';
import { Account } from '../_model/account';
declare var $:any;
@Component({
  selector: 'app-hotell-details',
  templateUrl: './hotell-details.component.html',
  styleUrls: ['./hotell-details.component.css']
})
export class HotellDetailsComponent implements OnInit {
  hotelId: any = 0;
  accountData : Account= {
    id: 0,
    email: "",
    password: "",
    fullName: "",
    phone: "",
    gender: "",
    birthday: "",
    image: "",
    role: 0
  }
  hotelData: Hotel = {
    id: 0,
    hotelName: "",
    address: "",
    image: null,
    shortdescription: "",
    email: "",
    phone: ""
  }
  CustomerId: number = 0;
  listRoomData: RoomData[] | undefined;
  minPrice = 0;
  currentDate = (new Date());
  roomBook: RoomBook ={
    id: 0,
    hoteRoomId: parseInt(this.hotelId),
    checkIn: "",
    checkOut: "",
    status: "",
    fullName: "",
    phone: "",
    description: "",
    creationTime: "",
    totalPrice: 0,
    accountId: 0,
  }
  roomBookData: RoomBookData ={
    id: 0,
    hoteRoomId: 0,
    checkIn: "",
    checkOut: "",
    status: "",
    fullName: "",
    phone: "",
    description: "",
    creationTime: "",
    totalPrice: 0,
    price: 0,
    hotelName: "",
    roomType: "",
    roomName: ""
  }
  totalNight: number = 0;

  constructor(private router: ActivatedRoute, private _service: HotelService,
    private _hotelRoom: HotelRoomService, private _roomBook : RoomBookService) { }

  ngOnInit(): void {
    this.accountData = JSON.parse(localStorage.getItem('user') || "0");
    this.hotelId = this.router.snapshot.paramMap.get('id');
    this._service.getById(this.hotelId).subscribe(res => {
      this.hotelData = res;
    })
    this.getAllRoom();
    this.CustomerId = JSON.parse(localStorage.getItem('user') || "0").id;
    console.log(this.hotelData);
    
  }


  getAllRoom() {
    this._hotelRoom.getAll("",this.hotelId).subscribe((res : any) => {
      this.listRoomData = res.listRoom;
      let price = 100000000;
      if(this.listRoomData)
      this.listRoomData.forEach((e:any) => {
        if (e.price <= price) {
          price = e.price;
        }
      })
      this.minPrice = price;
    })
  }

  getRoomById(data: any) {
    this.roomBookData ={
      id: 0,
      hoteRoomId: parseInt(this.hotelId),
      checkIn: "",
      checkOut: "",
      status: "",
      fullName: "",
      phone: "",
      description: "",
      creationTime: "",
      totalPrice: 0,
      price: data.price,
      hotelName: "",
      roomType: "",
      roomName: data.roomName
    }
    $("#roomBillModal").modal('show');
  }

  changeDate(event: any) {
    let day = Math.ceil((new Date(this.roomBookData.checkOut).getTime() - new Date(this.roomBookData.checkIn).getTime()) / (1000 * 60 * 60 * 24));
    this.totalNight = day > 0 ? day : 0;
  }

  createBill() {
    
    this.roomBook = {
      id: 0,
      hoteRoomId: parseInt(this.hotelId),
      checkIn: this.roomBookData.checkIn,
      checkOut: this.roomBookData.checkOut,
      status: "Chưa xác nhận",
      fullName: this.roomBookData.fullName,
      phone: this.roomBookData.phone,
      description: this.roomBookData.description,
      creationTime: (new Date).toLocaleDateString(),
      totalPrice: (this.totalNight * this.roomBookData.price),
      accountId: this.accountData.id,
    }

    this.currentDate.setDate(new Date().getDate() - 1).toString();
    if (this.totalNight === 0 || new Date(this.roomBookData.checkIn) < new Date(this.currentDate)) {
      alert("Ngày chưa hợp lệ!");
      return;
    } 

    this._roomBook.create(this.roomBook).subscribe((res:any) =>{
      if(res.success){
        $('#roomBillModal').modal('hide');
        alert("Đặt phòng thành công!");
        this.reset();
      }
      
    })
  }
  reset(){
    this.roomBookData ={
      id: 0,
      hoteRoomId: 0,
      checkIn: "",
      checkOut: "",
      status: "",
      fullName: "",
      phone: "",
      description: "",
      creationTime: "",
      totalPrice: 0,
      price: 0,
      hotelName: "",
      roomType: "",
      roomName: ""
    }

    this.roomBook={
      id: 0,
      hoteRoomId: parseInt(this.hotelId),
      checkIn: "",
      checkOut: "",
      status: "",
      fullName: "",
      phone: "",
      description: "",
      creationTime: "",
      totalPrice: 0,
      accountId: 0,
    }
  }
}
