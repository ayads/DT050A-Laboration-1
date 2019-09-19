package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class JoinMessage extends Message {

    public String joined = "";

	public JoinMessage(Client client) {
        this.joined = "User ID: " + client.getID() + " joined!";
	}
}