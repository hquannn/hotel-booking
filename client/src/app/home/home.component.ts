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
      console.log('Form is valid', this.contact);
      let _url = this.apiUrl + "Contact/sendEmail";
      this.http.post(_url, this.contact, {
        headers: { 'Content-Type': 'application/json' }
      }).subscribe(
        response => {
          console.log('Response:', response);
          alert('Your message has been sent. Thank you!');
        },
        // error => {
        //   console.error('Error:', error);
        //   this.errorMessage = 'An error occurred while sending the message.';
        // }
      );
    } else {
      console.log('Form is invalid');
    }
  }
}