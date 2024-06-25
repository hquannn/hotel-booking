import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatbotService {
  private apiUrl = 'http://localhost:8080/API/chatbot'; // Adjust this if your backend is on a different endpoint

  constructor(private http: HttpClient) { }

  sendMessage(userMessage: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = JSON.stringify({ message: userMessage });
    console.log('Sending JSON to backend:', body);
    return this.http.post<any>(this.apiUrl, body, { headers });
  }
}