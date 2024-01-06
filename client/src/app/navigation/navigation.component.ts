import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AccountService } from '../_service/account.service';
import { Account } from '../_model/account';
import { Router } from '@angular/router';
declare var $: any;
@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  email: string = "";
  passWord: string = "";
  dataAccount: Account = {
    id: 0,
    email: "",
    password: "",
    fullName: "",
    phone: "",
    gender: "",
    birthday: "",
    image: null,
    role: 0
  };
  isLogin: boolean = false;
  constructor(private _serviceAccount: AccountService,
    private router: Router) { }

  ngOnInit(): void {
    this.getCurrentUser();
  }

  login() {
    this._serviceAccount.login(this.email, this.passWord).subscribe(res => {
      if(res.success == false){
        alert("Email hoặc mật khẩu không chính xác!");
      }
      else{
        this.dataAccount = res;
        this.dataAccount.image =  (this.dataAccount.image === null ||  this.dataAccount.image.trim() === "") ? null :   res.image;
        this.isLogin = true;
        if(this.dataAccount.role === 0 ){
          this.router.navigateByUrl('InforMation');
        }
        if(this.dataAccount.role === 1 ){
          this.router.navigateByUrl('/');
        }

        localStorage.setItem('user', JSON.stringify(res));
        this._serviceAccount.setCurrentUser(res);
        $('#close_modal').click();
      }
    },
      error => {
        alert("Lỗi kết nối!");
      })
  }

  logout() {
    this._serviceAccount.logOut();
    this.isLogin = false;
  }

  getCurrentUser(){
    this._serviceAccount.currentUser$.subscribe(user =>{
      this.isLogin = !!user;
      this.dataAccount = user ?? {
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
    })
  }

  KtraKhachSan() {
    if (!this.isLogin) {
      alert("Đăng nhập để sử dụng các tính năng của trang web!");
    }
    else {
      this.router.navigateByUrl('Booking');
    }
  }

}
