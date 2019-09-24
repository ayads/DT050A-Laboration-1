package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class ResponseJoinMessage extends Message {

    public String responseJoinMessage = "";
    public int clientID = -1;

	public ResponseJoinMessage(Client client) {
        this.responseJoinMessage = "User ID " + client.getID() + " is now online!";
        this.clientID = client.getID();
	}
}