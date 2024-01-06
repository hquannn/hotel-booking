import { Component, OnInit } from '@angular/core';
import { Account } from '../_model/account';
import { AccountService } from '../_service/account.service';
declare var $:any;
@Component({
  selector: 'app-manage-account',
  templateUrl: './manage-account.component.html',
  styleUrls: ['./manage-account.component.css']
})
export class ManageAccountComponent implements OnInit {
  request: string = "";
  pageNumber: number = 1;
  status = "";
  totalPage: number = 10;
  paginationTitle: string = "";
  listData: any[] | undefined;
  data: Account = {
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

  constructor(private _service: AccountService){}
  ngOnInit(): void {
    this.getAll();
  }

  getAll(){
    this._service.getAll(this.request, this.pageNumber).subscribe((res)=>{
      this.listData = res.list;
      this.listData.forEach(result => {
        result.birthday = new Date(result.birthday).toLocaleDateString()
      })
      this.totalPage = Math.ceil(res.total / 10);
      this.paginationTitle = "Page " + this.pageNumber + " of " + (this.totalPage == 0 ? 1 : this.totalPage);
    })
  }

  search(event?: any) {
    if (event) {
      if (event.keyCode === 13) { 
        this.pageNumber = 1;
        this.getAll();
      }
    } else {
      this.pageNumber = 1;
      this.getAll();
    }
  }

  getData(data?:any){
    $('#modalCustomerRequireLabel').text('Tài khoản');
    if(data){
      this.data = data;
      const parts = data.birthday.split('/');
      if(parts[1].length == 1) parts[1] = "0" + parts[1] 
      this.data.birthday = `${parts[2]}-${parts[1]}-${parts[0]}`;
    }
    
    $('#modalCustomerRequire').modal('show');
  } 

  createOrUpdate() {
    this._service.updateAccount(this.data).subscribe(res => {
      this.closeModal();
      alert("Thành công!");
    }, error => {
      alert("Đã xảy ra lỗi gì đó! Hãy kiểm tra lại đường truyền!")
    })
  }

  closeModal() {
    this.getAll();
    $('#modalCustomerRequire').modal('hide');
    this.data = {
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
  }

  previousPage() {
    if (this.pageNumber > 1) {
      this.pageNumber -= 1;
      this.getAll();
    }
  }
  nextPage() {
    if (this.pageNumber < this.totalPage) {
      this.pageNumber += 1;
      this.getAll();
    }
  }

  getFormattedDate(date: Date): string {
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear().toString();
    return `${year}-${month}-${day}`;
  }
  

  delete(id:number){
    if(confirm("Bạn có chắc chắc muốn xóa tài khoản này?")){
      this._service.deleteAccount(id).subscribe(res =>{
        this.search();
      })
    }
  }
  onselectionFile(file: any) {
    var reader = new FileReader();
    reader.readAsDataURL(file.target.files[0]);
    reader.onloadend = (value: any) => {
      this.data.image = value.target.result.toString();
    }
  }
}
