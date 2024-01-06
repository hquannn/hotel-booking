import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "Register", component: RegisterUserComponent},
  {path: "InforMation", component: InformationAccountComponent},
  {path: "ManageAccount", component: ManageAccountComponent},
  {path: "Hotel", component: HotelComponent},
  {path: "Hotel/:id", component: HotellDetailsComponent},
  {path: "Room", component: RoomComponent},
  {path: "HotelRoom/:id", component: HotelRoomComponent},
  {path: "Booking", component: BookingComponent},
  {path: "ManageRoomBook", component: ManageRoomBookComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
