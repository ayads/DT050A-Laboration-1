package se.miun.distsys.messages;

import java.util.ArrayList;
import java.util.List;

import se.miun.distsys.clients.Client;

public class ResponseJoinMessage extends Message {
    public int clientID;

	public ResponseJoinMessage(Client myClient ) {
        clientID = myClient.getID();
	}
}