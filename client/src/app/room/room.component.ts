import { Component, OnInit } from '@angular/core';
import { Room } from '../_model/room';
import { RoomService } from '../_service/room.service';
declare var $:any;
@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {
  hotelId: any = 0;
  listData: Room[] | undefined;
  roomId:  number = 0;
  dataRoom: Room = {
    id : 0,
    roomName : "",
    roomType : "",
    roomBedType : "",
    roomSize : 0,
    roomCapacity : 0,  
    roomDescription : ""
  };
  constructor( private _service: RoomService) { }

  ngOnInit(): void {
    this.getAll();
  }


  getAll() {
    this._service.getAll("").subscribe(res => {
      console.log(res.listRoom);
      this.listData = res.listRoom;
    })
  }

  createOrUpdateRoom() {
    this._service.createOrUpdate(this.dataRoom).subscribe(res => {
      if(res){
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

  getByRoomData(data? : Room){
    if(data){
      this.dataRoom =  data;
    }
      this.showModal();
  }


  reset() {
    this.dataRoom = {
      id : 0,
    roomName : "",
    roomType : "",
    roomBedType : "",
    roomSize : 0,
    roomCapacity : 0,  
    roomDescription : ""
    }
  }

  
}
