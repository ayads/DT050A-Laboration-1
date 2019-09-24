package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class JoinMessage extends Message {

    public String joined = "";
    public Client client;

	public JoinMessage(Client client) {
        this.client = client;
        this.joined = "User ID " + client.getID() + " is now online!";
	}
}