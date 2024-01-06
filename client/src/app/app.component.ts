import { Component, OnInit } from '@angular/core';
import { JsonHubProtocol } from '@microsoft/signalr';
import { AccountService } from './_service/account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private _accountService: AccountService){}
  ngOnInit(): void {
    this.setCurrentUser();
  }

  setCurrentUser(){
    if(localStorage.getItem('user') !== null){
      const user : any = JSON.parse(localStorage.getItem('user') || ""); 
      this._accountService.setCurrentUser(user);
    }
  }
}

