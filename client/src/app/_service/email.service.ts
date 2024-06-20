import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  private apiUrl = "http://localhost:8080/API/"; // Adjust the API URL accordingly

  constructor(private http: HttpClient) { }

  sendEmail(emailData: any) {
    return this.http.post(this.apiUrl + "Contact/sendConfirm", emailData, {
      headers: { 'Content-Type': 'application/json' }
    });
  }
}
