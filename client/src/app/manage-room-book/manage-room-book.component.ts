import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RoomBookService } from '../_service/room-book.service';
import { EmailService } from '../_service/email.service'; // Adjust the path accordingly
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
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

  constructor(
    private _service: RoomBookService,
    private _emailService: EmailService
  ){}
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
    if (check) {
      this.data.status = "Đã xác nhận";
    } else {
      if (!confirm("Bạn có chắc muốn hủy đơn đặt phòng này?")) return;
      this.data.status = "Từ chối";
    }

    this._service.updateStatus(this.data).subscribe(res => {
      alert("Thành công!");
      this.getAll();
      // Prepare email data
      const emailData = {
        // to: this.data.email, // The user's email
        subject: `Booking confirmed ${this.data.status}`,
        message: `Dear ${this.data.fullName},<br>You have confirmed your booking.`
      };
      // Send email
      this._emailService.sendEmail(emailData).subscribe(
        (response: any) => {
          console.log("Email sent successfully.");
        },
        (error: any) => {
          console.error("Error sending email:", error);
        }
      );
    }, error => {
      alert("Đã xảy ra lỗi gì đó! Hãy kiểm tra lại đường truyền!");
    });
  }

  exportToExcel(): void {
    const header = ['Họ tên', 'Điện thoại', 'Ngày nhận', 'Ngày trả', 'Tên khách sạn', 'Loại phòng', 'Đơn giá', 'Tổng tiền', 'Trạng thái'];

    const data = this.listData?.map(item => ({
      'Họ tên': item.fullName,
      'Điện thoại': item.phone,
      'Ngày nhận': item.checkIn,
      'Ngày trả': item.checkOut,
      'Tên khách sạn': item.hotelName,
      'Loại phòng': item.roomName,
      'Đơn giá': item.price,
      'Tổng tiền': item.totalPrice,
      'Trạng thái': item.status
    })) || [];

    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data, { header });
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };

    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, 'roombook');
  }

  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], { type: 'application/octet-stream' });
    saveAs(data, `${fileName}_${new Date().getTime()}.xlsx`);
  }
  
  showModal: boolean= false;
  openModal() {
    this.showModal = true;
    
  }
  
  closeModal() {
    this.showModal = false;
    
  }
}


