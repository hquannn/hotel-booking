import { Component } from '@angular/core';
import { ChatbotService } from '../_service/chatbox.service';

interface Message {
  text: string;
  sender: 'user' | 'bot';
  timestamp: Date;
}

@Component({
  selector: 'app-chatbox',
  templateUrl: './chatbox.component.html',
  styleUrls: ['./chatbox.component.css']
})
export class ChatboxComponent {
  isModalVisible: boolean = false;
  isChatboxVisible: boolean = false;
  userMessage: string = '';
  messages: Message[] = [];

  constructor(private chatbotService: ChatbotService) {}

  toggleModal() {
    this.isModalVisible = !this.isModalVisible;
  }

  toggleChatbox() {
    this.isChatboxVisible = !this.isChatboxVisible;
  }

  preventClose(event: MouseEvent) {
    event.stopPropagation();
  }

  sendMessage(): void {
    if (this.userMessage.trim()) {
      const userMessage: Message = {
        text: this.userMessage,
        sender: 'user',
        timestamp: new Date()
      };
      this.messages.push(userMessage);
      this.userMessage = '';

      this.chatbotService.sendMessage(userMessage.text).subscribe((res) => {
        const botMessage: Message = {
          text: res.choices[0].message.content,
          sender: 'bot',
          timestamp: new Date()
        };
        this.messages.push(botMessage);
      }, (error) => {
        console.error('Error sending message:', error);
        const errorMessage: Message = {
          text: 'Error communicating with the server. Please try again later.',
          sender: 'bot',
          timestamp: new Date()
        };
        this.messages.push(errorMessage);
      });
    }
  }
}
