import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.miun.distsys.GroupCommuncation;
import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.listeners.JoinMessageListener;
import se.miun.distsys.listeners.LeaveMessageListener;
import se.miun.distsys.listeners.ResponseJoinMessageListener;
import se.miun.distsys.messages.ChatMessage;
import se.miun.distsys.messages.JoinMessage;
import se.miun.distsys.messages.LeaveMessage;
import se.miun.distsys.messages.ResponseJoinMessage;

//Skeleton code for Distributed systems 9hp, DT050A

public class Program implements ChatMessageListener, JoinMessageListener, ResponseJoinMessageListener, LeaveMessageListener{
	boolean runProgram = true;
	GroupCommuncation gc = null;	
		
	public Program() {
		gc = new GroupCommuncation();		
		gc.setChatMessageListener(this);
		gc.setJoinMessageListener(this);
		gc.setResponseJoinMessageListener(this);
		gc.setLeaveMessageListener(this);
		System.out.println("Group Communcation Started");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		while(runProgram) {			
			try {
				
				System.out.println("Write message to send: ");	
				String chat = br.readLine();			
				gc.sendChatMessage(gc.activeClient, chat);
				gc.sendJoinMessage(gc.activeClient);
				gc.sendResponseJoinMessage();
				
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		gc.shutdown();
	}

	@Override
	public void onIncomingChatMessage(ChatMessage chatMessage) {		
		System.out.println("Incoming chat-message: " + chatMessage.chat);	
	}

	@Override
	public void onIncomingJoinMessage(JoinMessage joinMessage) {		
		System.out.println("Incoming join-message id: " + joinMessage.clientID);	
	}

	@Override
	public void onIncomingResponseJoinMessage(ResponseJoinMessage responseJoinMessage) {		
		System.out.println("Incoming response-join-message id: " + responseJoinMessage.clientID);	
	}

	@Override
	public void onIncomingLeaveMessage(LeaveMessage leaveMessage) {
		System.out.println("Incoming leave-message id: " + leaveMessage.clientID);	
	}

	public static void main(String[] args) {
		Program program = new Program();
	}
}