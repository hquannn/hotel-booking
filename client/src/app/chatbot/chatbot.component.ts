import { Component, OnInit } from '@angular/core';
import { ChatbotService } from '../_service/chatbot.service';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent implements OnInit {
  userMessage: string = '';
  responseMessage: string = '';

  constructor(private chatbotService: ChatbotService) { }

  ngOnInit(): void {}

  sendMessage(): void {
    if (this.userMessage.trim()) {
      this.chatbotService.sendMessage(this.userMessage).subscribe((res) => {
        this.responseMessage = res.choices[0].message.content;
      }, (error) => {
        console.error('Error sending message:', error);
        this.responseMessage = 'Error communicating with the server. Please try again later.';
      });
    }
  }
}