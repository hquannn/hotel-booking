import { Component, OnInit } from '@angular/core';
import { JsonHubProtocol } from '@microsoft/signalr';
import { AccountService } from './_service/account.service';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  showNavigationAndFooter: boolean = true;

  constructor(private _accountService: AccountService, private router: Router){}
  ngOnInit(): void {
    this.setCurrentUser();

    this.router.events.pipe(
      filter((event: any) => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      // Update this array with the paths where you want to hide the navigation and footer
      const hiddenRoutes = [ 'admin'];
      this.showNavigationAndFooter = !hiddenRoutes.some(route => event.urlAfterRedirects.includes(route));
    });
  }
  
  setCurrentUser(){
    if(localStorage.getItem('user') !== null){
      const user : any = JSON.parse(localStorage.getItem('user') || ""); 
      this._accountService.setCurrentUser(user);
    }
  }
}

