import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { RegisterUserComponent } from './register-user/register-user.component';
import { InformationAccountComponent } from './information-account/information-account.component';
import { ManageAccountComponent } from './manage-account/manage-account.component';
import { HotelComponent } from './hotel/hotel.component';
import { RoomComponent } from './room/room.component';
import { HotelRoomComponent } from './hotel-room/hotel-room.component';
import { HotellDetailsComponent } from './hotell-details/hotell-details.component';
import { BookingComponent } from './booking/booking.component';
import { ManageRoomBookComponent } from './manage-room-book/manage-room-book.component';
@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    FooterComponent,
    HomeComponent,
    RegisterUserComponent,
    InformationAccountComponent,
    ManageAccountComponent,
    HotelComponent,
    RoomComponent,
    HotelRoomComponent,
    HotellDetailsComponent,
    BookingComponent,
    ManageRoomBookComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
