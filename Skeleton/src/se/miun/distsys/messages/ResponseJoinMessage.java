package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class ResponseJoinMessage extends Message {
    
    public int clientID;

	public ResponseJoinMessage(Client client) {
        this.clientID = client.getID();
	}
}