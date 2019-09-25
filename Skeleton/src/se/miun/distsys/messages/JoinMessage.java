package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class JoinMessage extends Message {
    
    public int clientID;

	public JoinMessage(Client client) {
        this.clientID = client.getID();
	}
}