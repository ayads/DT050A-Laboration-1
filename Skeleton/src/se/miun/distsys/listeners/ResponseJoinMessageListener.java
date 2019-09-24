package se.miun.distsys.listeners;

import se.miun.distsys.messages.ResponseJoinMessage;

public interface ResponseJoinMessageListener {
    public void onIncomingResponseJoinMessage(ResponseJoinMessage responseJoinMessage);
}