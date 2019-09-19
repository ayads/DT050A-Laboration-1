package se.miun.distsys;

import java.util.ArrayList;
import java.util.List;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.listeners.JoinMessageListener;
import se.miun.distsys.messages.ChatMessage;
import se.miun.distsys.messages.JoinMessage;
import se.miun.distsys.messages.Message;
import se.miun.distsys.messages.MessageSerializer;
import se.miun.distsys.clients.Client;
import se.miun.distsys.clients.UniqueIdentifier;


/**
 * TODO: 
 * Leave message(class)
 * Join message (class)
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

	//Active clients
	public List<Client> activeClientList = new ArrayList<Client>();

	public GroupCommuncation() {			
		try {
			runGroupCommuncation = true;
			Client activeClient = new Client(InetAddress.getByName("255.255.255.255"), datagramSocketPort, UniqueIdentifier.getUniqueIdentifier());				
			datagramSocket = new MulticastSocket(datagramSocketPort);	
			ReceiveThread rt = new ReceiveThread();
			rt.start();
			sendJoinMessage(activeClient);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
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
					joinMessageListener.onIncomingJoinMessage(joinMessage); //Should show for the user.
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

	public void sendJoinMessage(Client client) {
		try {
			JoinMessage joinMessage = new JoinMessage(client);
			System.out.println(joinMessage.joined);
			activeClientList.add(client);
			byte[] sendData = messageSerializer.serializeMessage(joinMessage);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
					InetAddress.getByName("255.255.255.255"), datagramSocketPort);
			datagramSocket.send(sendPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setJoinMessageListener(JoinMessageListener listener) {
		this.joinMessageListener = listener;
	}

	public void setChatMessageListener(ChatMessageListener listener) {
		this.chatMessageListener = listener;
	}
	
}
