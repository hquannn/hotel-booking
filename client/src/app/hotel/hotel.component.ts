import { Component, OnInit } from '@angular/core';
import { HotelService } from '../_service/hotel.service';
import { Hotel } from '../_model/hotel';
import { Router } from '@angular/router';
declare var $: any;
@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {
  request: string = "";
  listData: Hotel[] | undefined;
  data: Hotel = {
    id: 0,
    hotelName: "",
    address: "",
    image: "",
    shortdescription: "",
    email: "",
    phone: ""
  };

  constructor(private _service: HotelService,private router: Router) { }
  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this._service.getAll(this.request).subscribe((res) => {
      this.listData = res;
      this.listData?.forEach(result =>{
        result.image = (result.image == null || result.image.trim() == "") ? null : result.image;
      })
      console.log(res);
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

  getData(data?: any) {
    $('#modalHotelLabel').text('Khách sạn');
    if (data) {
      this.data = data;
    }
    $('#modalHotel').modal('show');
  }

  createOrUpdate() {
    this._service.createOrUpdate(this.data).subscribe(res => {
      if (res) {
        alert("Thành công!");
        this.closeModal();
      }
      else {
        alert("Thất bại!");
      }

    }, error => {
      alert("Đã xảy ra lỗi gì đó! Hãy kiểm tra lại đường truyền!")
    })
  }

  closeModal() {
    this.getAll();
    this.reset();
    $('#modalHotel').modal('hide');
  }


  delete(id: number) {
    if (confirm("Bạn có chắc chắc muốn xóa tài khoản này?")) {
      this._service.delete(id).subscribe(res => {
        this.search();
      })
    }
  }
  reset() {
    this.data = {
      id: 0,
      hotelName: "",
      address: "",
      image: "",
      shortdescription: "",
      email: "",
      phone: ""
    };
  }

  updateRoomHotel(data:any){
    this.router.navigateByUrl('HotelRoom/' + data.id);
  }
  hoteldetails(data:any){
    this.router.navigateByUrl('Hotel/' + data.id);
  }
  onselectionFile(file: any) {
    var reader = new FileReader();
    reader.readAsDataURL(file.target.files[0]);
    reader.onloadend = (value: any) => {
      this.data.image = value.target.result.toString();
    }
  }
}
