import { Component, OnInit } from '@angular/core';
import { Account } from '../_model/account';
import { AccountService } from '../_service/account.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {
  dataAccount: Account = {
    id: 0,
    email: "",
    password: "",
    fullName: "",
    phone: "",
    gender: "Nam",
    birthday: "",
    image: "",
    role: 0
  };
  checkBox: boolean = false;
  regEmail = /^\w+@[a-zA-Z]+\.com$/i;
  confirmPass: string = "";

  constructor(private _serviceAccount: AccountService) { }

  ngOnInit(): void {
  }


  register() {
    if (this.isValid()) {
      if (!this.regEmail.test(this.dataAccount.email)) {
        alert("Email không hợp lệ!!!");
      }
      else if (!this.checkBox) {
        alert("Đồng ý điều khoản của chúng tôi!")
      }
      else if (this.confirmPass !== this.dataAccount.password) {
        alert("Mật xác thực chưa chính xác!")
      }
      else {
        this.dataAccount.password.trim();
        this.dataAccount.email.trim();
        this.dataAccount.fullName.trim();
        this._serviceAccount.registerAccount(this.dataAccount).subscribe(res => {
          this.reset();
          alert("Đăng ký thành công!");
        },
          error => {
            alert("Email đã được sử dụng!");
          });
      }
    }
    else {
      alert("Nhập đủ các ô dữ liệu!")
    }

  }

  isValid() {
    if (this.dataAccount.fullName.length <= 0 || this.dataAccount.phone.length <= 0 || this.dataAccount.email.length <= 0 ||
      this.dataAccount.password.length <= 0 || this.confirmPass.length <= 0) return false;
    return true;
  }

  reset() {
    this.dataAccount = {
        id: 0,
        email: "",
        password: "",
        fullName: "",
        phone: "",
        gender: "Nam",
        birthday: "",
        image: "",
        role: 0
    };
    this.confirmPass = "";
  }
}
