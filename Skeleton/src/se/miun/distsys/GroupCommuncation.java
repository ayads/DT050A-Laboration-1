package se.miun.distsys;

import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.ir.JoinPredecessor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.messages.ChatMessage;
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

	//Active clients
	public List<Client> clients = new ArrayList<Client>();

	public GroupCommuncation() {			
		try {
			runGroupCommuncation = true;				
			datagramSocket = new MulticastSocket(datagramSocketPort);	
			ReceiveThread rt = new ReceiveThread();
			
			rt.start();
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
					handleDatagramPacket(datagramPacket);
					handleMessage(receivedMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		private void handleDatagramPacket (DatagramPacket datagramPacket) {
			String string = new String(datagramPacket.getData()); 
			boolean isConnected = true;
			if (!(string.isEmpty())){
				int clientId = UniqueIdentifier.getUniqueIdentifier();
				String clientName = "ex:name";
				InetAddress ipAddress = datagramPacket.getAddress();
				int portNumber = datagramPacket.getPort();
				if(isConnected){
					clients.add(new Client(clientName, ipAddress, portNumber, clientId, isConnected));
					System.out.println(clients.get(0).getID());
				}
			}else{
				isConnected = false;
				System.out.println(string);
			}
		}

		private void handleMessage (Message message) {
			
			if(message instanceof ChatMessage) {				
				ChatMessage chatMessage = (ChatMessage) message;				
				if(chatMessageListener != null){
					chatMessageListener.onIncomingChatMessage(chatMessage);
				}
			} else {				
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

	public void setChatMessageListener(ChatMessageListener listener) {
		this.chatMessageListener = listener;		
	}
	
}
