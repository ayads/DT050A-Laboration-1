import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.EventQueue;

import se.miun.distsys.GroupCommuncation;
import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.messages.ChatMessage;
import se.miun.distsys.clients.Client;


//Skeleton code for Distributed systems 9hp, DT050A

public class WindowProgram implements ChatMessageListener, ActionListener {
	JFrame frame;
	JTextPane txtpnChat = new JTextPane();
	JTextPane txtpnMessage = new JTextPane();
	JTextPane clientId = new JTextPane();
	JTextPane clientStatus = new JTextPane();
	JTextPane connectionStatus = new JTextPane();
	
	GroupCommuncation gc = null;
	String[] activeClient = {"client 1", "client 2", "client 3"}; //an example of client names you will have in the list
	
	public WindowProgram() {
		initializeFrame();
		gc = new GroupCommuncation();		
		gc.setChatMessageListener(this);
		System.out.println("Group Communcation Started");
	}

	private void initializeFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(4, 1, 0, 0));
		/*
		*	Show a list of clients 
		*	in the first row, if you 
		*	have many users so will 
		*	you be able to scroll
		*/
		JList<String> activeClientList = new JList<String>(activeClient);
		JScrollPane activeClientListScrollPane = new JScrollPane(activeClientList);
		frame.getContentPane().add(activeClientListScrollPane);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(txtpnChat);
		txtpnChat.setEditable(false);	
		txtpnChat.setText("--== Group Chat ==--");
		
		JScrollPane scrollPaneClients = new JScrollPane();
		frame.getContentPane().add(scrollPaneClients);
		scrollPaneClients.setViewportView(connectionStatus);
		connectionStatus.setEditable(false);	
		connectionStatus.setText("Connection Status");
		frame.getContentPane().add(connectionStatus);

		
		txtpnMessage.setText("Message: ");
		frame.getContentPane().add(txtpnMessage);
		
		JButton btnSendChatMessage = new JButton("Send Chat Message");
		btnSendChatMessage.addActionListener(this);
		btnSendChatMessage.setActionCommand("send");
		frame.getContentPane().add(btnSendChatMessage);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(WindowEvent winEvt) {
	            gc.shutdown();
	        }
	    });
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equalsIgnoreCase("send")) {
			gc.sendChatMessage(txtpnMessage.getText());
		}		
	}

	@Override
	public void onIncomingChatMessage(ChatMessage chatMessage) {
		txtpnChat.setText(chatMessage.chat + "\n" + txtpnChat.getText());
		for(int i = 0; (i < gc.clients.size()) && (gc.clients.get(i).status == true); i++){
			int clientId = gc.clients.get(i).getID();
			String clientIdAsString = String.valueOf(clientId);
			connectionStatus.setText(clientIdAsString + " joined" + "\n" + connectionStatus.getText());
		}
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowProgram window = new WindowProgram();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
