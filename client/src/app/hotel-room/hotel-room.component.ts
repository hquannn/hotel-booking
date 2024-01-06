import { Component, OnInit } from '@angular/core';
import { HotelRoomService } from '../_service/hotel-room.service';
import { HotelRoom } from '../_model/hotemRoom';
import { RoomData } from '../_model/roomData';
import { ActivatedRoute } from '@angular/router';
import { RoomService } from '../_service/room.service';
import { Room } from '../_model/room';
declare var $:any;
@Component({
  selector: 'app-hotel-room',
  templateUrl: './hotel-room.component.html',
  styleUrls: ['./hotel-room.component.css']
})
export class HotelRoomComponent implements OnInit {
  hotelId: any = 0;
  listData: RoomData[] | undefined;
  listRoom: Room[] |undefined;
  roomId:  number = 0;
  dataRoom: HotelRoom = {
    id: 0,
    roomId: 0,
    hotelId: 0,
    price: 0,
    isActive: true,
    image: "",
  };
  constructor(private router: ActivatedRoute, private _service: HotelRoomService, private _roomService : RoomService) { }

  ngOnInit(): void {
    this.hotelId = this.router.snapshot.paramMap.get('id');
    this.getAll();
    this._roomService.getAll("").subscribe(res=>{
      this.listRoom = res.listRoom;
    })
  }


  getAll() {
    this._service.getAll("", this.hotelId).subscribe(res => {
      console.log(res.listRoom);
      this.listData = res.listRoom;
    })
  }

  createOrUpdateRoom() {
    this.dataRoom.hotelId = parseInt(this.hotelId);
    this.dataRoom.roomId = parseInt(this.dataRoom.roomId.toString());
    this._service.createOrUpdate(this.dataRoom).subscribe((res:any) => {
      if(!res.success) alert("Loại phòng đang được sử dụng!")
      else if(res.success){
        alert("Thành công");
        this.reset();
        this.getAll();
        this.closeModal();
      }
      else alert("Thất bại!")
      
    },
      error => {
        alert("Đã xảy ra lỗi gì đó! Hãy thao tác lại!");
      })
  }

  deleteRoom(id: number) {
    if (confirm("Bạn có chắc chắc muốn thực hiện thao tác này không?")) {
      this._service.delete(id).subscribe(res => {
        alert("Xóa phòng thành công!");
        this.getAll();
      })
    }
  }

  showModal(){
    if(this.dataRoom.id == 0){
      $('#staticBackdropLabel').text("Thêm phòng");
    }else $('#staticBackdropLabel').text("Sửa phòng")
    $('#staticBackdrop').modal('show');
  }
  closeModal(){
    this.roomId == 0;
    $('#staticBackdrop').modal('hide');
    this.reset();
  }

  getByRoomData(data? : HotelRoom){
    if(data){
      this.dataRoom = {
        id: data.id,
        roomId: data.roomId,
        hotelId: data.hotelId,
        price: data.price,
        isActive: data.isActive,
        image: data.image
      };
    }
      this.showModal();
  }


  reset() {
    this.dataRoom = {
      id: 0,
      roomId: 0,
      hotelId: 0,
      price: 0,
      isActive: true,
      image: "",
    };
  }
  onselectionFile(file: any) {
    var reader = new FileReader();
    reader.readAsDataURL(file.target.files[0]);
    reader.onloadend = (value: any) => {
      this.dataRoom.image = value.target.result.toString();
    }
  }
  
}
