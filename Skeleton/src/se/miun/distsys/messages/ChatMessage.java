package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class ChatMessage extends Message {

	public String chat = "";
	
	public ChatMessage(String chat) {
		this.chat = chat;
	}
}
