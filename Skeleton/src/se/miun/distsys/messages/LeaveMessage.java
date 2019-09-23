package se.miun.distsys.messages;

import se.miun.distsys.clients.Client;

public class LeaveMessage extends Message {

    public String left = "";
    public int clientID = -1;

	public LeaveMessage(Client client) {
        this.left = "User ID " + client.getID() + " is now offline!";
        this.clientID = client.getID();
	}
}