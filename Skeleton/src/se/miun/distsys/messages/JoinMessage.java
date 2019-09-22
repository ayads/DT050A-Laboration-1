package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class JoinMessage extends Message {

    public String joined = "";
    public int clientID = -1;

	public JoinMessage(Client client) {
        this.joined = "User ID " + client.getID() + " is now online!";
        this.clientID = client.getID();
	}
}