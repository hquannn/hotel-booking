import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  readonly apiUrl = "http://localhost:8080/API/";
  contact = {
    name: '',
    email: '',
    subject: '',
    message: ''
  };
  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  onSubmit(form: NgForm) {
    if (form.valid) {
      let _url = this.apiUrl + "Contact/sendEmail";
      this.http.post(_url, this.contact, {
        headers: { 'Content-Type': 'application/json' }
      }).subscribe(
        response => {
          alert('Your message has been sent. Thank you!');
        },
        error => {
          this.errorMessage = 'An error occurred while sending the message.';
        }
      );
    }
  }
}
