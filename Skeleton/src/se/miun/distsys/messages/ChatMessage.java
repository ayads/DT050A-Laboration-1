package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class ChatMessage extends Message {

	public String chat = "";
	public int clientID;
	
	public ChatMessage(Client client, String chat) {
		this.clientID = client.getID();
		this.chat = chat;
	}
}
