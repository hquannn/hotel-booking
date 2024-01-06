import { Component, OnInit } from '@angular/core';
import { AccountService } from '../_service/account.service';
import { Account } from '../_model/account';

@Component({
  selector: 'app-information-account',
  templateUrl: './information-account.component.html',
  styleUrls: ['./information-account.component.css']
})
export class InformationAccountComponent implements OnInit {

  userData: Account = {
    id: 0,
    email: "",
    password: "",
    fullName: "",
    phone: "",
    gender: "",
    birthday: "",
    image: "",
    role: 0
  };
  currentPassword = "";
  confirmPassword = "";
  dateFormat = "";
  fullName = "";
  phone = "";
  image: string| null = "" ;
  email = "";
  constructor(private _service: AccountService){}
  ngOnInit(): void {
    this.setup();

  }

  setup(){
    this.userData = JSON.parse(localStorage.getItem('user') || "0");
    this.userData.image = (this.userData.image === null || this.userData.image.trim() === "") ? null : this.userData.image;
    this.userData.birthday = new Date(this.userData.birthday).toLocaleDateString()
    this.userData.password = "";

    this.fullName = this.userData.fullName;
    this.phone = this.userData.phone;
    this.image = (this.userData.image === null || this.userData.image.trim() === "") ? null : this.userData.image;
    this.email=this.userData.email;

    let parts = this.userData.birthday.split('/');
    let date = new Date(`${parts[1]}/${parts[0]}/${parts[2]}`);
    this.dateFormat = this.getFormattedDate(date);
  }


  updateAccount(){
    if(this.fullName.trim() === "") return alert("Họ tên không được để trống!");
    if(this.dateFormat === null || this.dateFormat.trim() === "") return alert("Ngày không hợp lệ!");
   

    if(this.phone.trim() === "") return alert("Điện thoại không được để trống!");

    
    this.userData.fullName = this.fullName.trim();
    this.userData.phone = this.phone.trim();
    this.userData.image = this.image ?? "";
    
    this.userData.birthday = this.dateFormat;

    this._service.updateAccount(this.userData).subscribe(res=>{
      localStorage.setItem('user', JSON.stringify(this.userData));
      this._service.setCurrentUser(this.userData);
      this.userData.birthday = new Date(this.userData.birthday).toLocaleDateString()
      alert("Cập nhật thành công!");
    })
  }

  changePassword(){
    if(this.currentPassword.trim() === "") return alert("Mật khẩu hiện tại KHÔNG được để trống!")
    if(this.userData.password.trim() === "") return alert("Mật khẩu mới KHÔNG được để trống!")
    if(this.confirmPassword.trim() === "") return alert("Xác nhận Mật khẩu KHÔNG được để trống!")
    if(this.userData.password.trim() !== this.confirmPassword.trim()) return alert("Mật khẩu xác nhận KHÔNG khớp!")


    this._service.updatePassword(this.userData.email,this.userData.password , this.currentPassword).subscribe(res=>{
      if(res){
        this.userData.password = "";
        this.currentPassword = "";
        this.confirmPassword = "";
        alert("Đổi mật khẩu thành công!");
      }
      else{
        alert("Mật khẩu hiện tại KHÔNG đúng!");
      }
    },
    error =>{
      alert("Đã xảy ra lỗi! Hãy kiểm tra lại đường truyền!");
    })
  }
  onselectionFile(file: any) {
    var reader = new FileReader();
    reader.readAsDataURL(file.target.files[0]);
    reader.onloadend = (value: any) => {
      this.image = value.target.result.toString();
    }
  }
  getFormattedDate(date: Date): string {

    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear().toString();
    return `${year}-${month}-${day}`;
  }
}