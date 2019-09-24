package se.miun.distsys;

import java.util.ArrayList;
import java.util.List;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.listeners.JoinMessageListener;
import se.miun.distsys.listeners.LeaveMessageListener;
import se.miun.distsys.listeners.ResponseJoinMessageListener;
import se.miun.distsys.messages.ChatMessage;
import se.miun.distsys.messages.JoinMessage;
import se.miun.distsys.messages.LeaveMessage;
import se.miun.distsys.messages.Message;
import se.miun.distsys.messages.MessageSerializer;
import se.miun.distsys.messages.ResponseJoinMessage;
import se.miun.distsys.clients.Client;
import se.miun.distsys.clients.UniqueIdentifier;


/**
 * TODO: 
 * Leave message(class)
 * Join message (class)
 * joinMessageListener(containes sendResponsJoinMessage ) 
 * respondJoinMessage
 * ResponseJoinMessage (class)
 * ResponseJoinMessagelistener
 * Obs!: we need to check whether the Client ID already exists in the activeClientList 
 * sendJoinMessage
 * sendLeaveMessage
 * Remove datagramHandler
 */


public class GroupCommuncation {
	
	private int datagramSocketPort = 2019; // port number for the communication		
	DatagramSocket datagramSocket = null;	
	boolean runGroupCommuncation = true;	
	MessageSerializer messageSerializer = new MessageSerializer();
	
	//Listeners
	ChatMessageListener chatMessageListener = null;
	JoinMessageListener joinMessageListener = null;
	LeaveMessageListener leaveMessageListener = null;
	ResponseJoinMessageListener responseJoinMessageListener = null; 

	//Active clients
	public List<Client> activeClientList = new ArrayList<Client>();

	public GroupCommuncation() {
		try {
			runGroupCommuncation = true;
			datagramSocket = new MulticastSocket(datagramSocketPort);	
			ReceiveThread rt = new ReceiveThread();
			rt.start();
			sendJoinMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		sendLeaveMessage(activeClientList.get(0));
		runGroupCommuncation = false;
	}
	
	class ReceiveThread extends Thread{
		
		@Override
		public void run() {
			byte[] buffer = new byte[65536];		
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
			while(runGroupCommuncation) {
				try {
					datagramSocket.receive(datagramPacket);										
					byte[] packetData = datagramPacket.getData();					
					Message receivedMessage = messageSerializer.deserializeMessage(packetData);				
					handleMessage(receivedMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void handleMessage (Message message) {
			
			if(message instanceof ChatMessage) {
				ChatMessage chatMessage = (ChatMessage) message;
				if(chatMessageListener != null){
					chatMessageListener.onIncomingChatMessage(chatMessage);
				}
			} else if (message instanceof JoinMessage) {
				JoinMessage joinMessage = (JoinMessage) message;
				if (joinMessageListener != null) {
					joinMessageListener.onIncomingJoinMessage(joinMessage);
				}
			} else if (message instanceof LeaveMessage) {
				LeaveMessage leaveMessage = (LeaveMessage) message;
				if (leaveMessageListener != null) {
					leaveMessageListener.onIncomingLeaveMessage(leaveMessage);
				}
			} else if (message instanceof ResponseJoinMessage) {
				ResponseJoinMessage responseJoinMessage = (ResponseJoinMessage) message;
				if (responseJoinMessageListener != null) {
					responseJoinMessageListener.onIncomingResponseJoinMessage(responseJoinMessage);
				}
			} 
			else {
				System.out.println("Unknown message type");
			}
		}
	}
	
	public void sendChatMessage(String chat) {
		try {
			ChatMessage chatMessage = new ChatMessage(chat);
			byte[] sendData = messageSerializer.serializeMessage(chatMessage);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
					InetAddress.getByName("255.255.255.255"), datagramSocketPort);
			datagramSocket.send(sendPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendJoinMessage() {
		try {
			Thread.sleep(250);
			Client activeClient = new Client(InetAddress.getByName("255.255.255.255"), datagramSocketPort, 
					UniqueIdentifier.getUniqueIdentifier());
			activeClientList.add(activeClient);
			JoinMessage joinMessage = new JoinMessage(activeClient);
			byte[] sendData = messageSerializer.serializeMessage(joinMessage);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
					InetAddress.getByName("255.255.255.255"), datagramSocketPort);
			datagramSocket.send(sendPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendLeaveMessage(Client inactiveClient) {
		try {
			//TODO: Implement sendLeaveMessage!
			activeClientList.remove(activeClientList.indexOf(inactiveClient));
			LeaveMessage leaveMessage = new LeaveMessage(inactiveClient);
			byte[] sendData = messageSerializer.serializeMessage(leaveMessage);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
					InetAddress.getByName("255.255.255.255"), datagramSocketPort);
			datagramSocket.send(sendPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendResponseJoinMessage(Client inactiveClient) {
		try {
			//TODO: Implement function!
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setChatMessageListener(ChatMessageListener listener) {
		this.chatMessageListener = listener;
	}

	public void setJoinMessageListener(JoinMessageListener listener) {
		this.joinMessageListener = listener;
	}

	public void setLeaveMessageListener(LeaveMessageListener listener) {
		this.leaveMessageListener = listener;
	}

	public void setResponseJoinMessageListener(ResponseJoinMessageListener listener) {
		this.responseJoinMessageListener = listener;
	}
}
