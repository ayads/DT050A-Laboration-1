# DT050A-Distributed-Systems-laboration-1
Laboration 1 will be a simple group communication program with no ordering of the messages.

## Goal
The main goal of this laboration is to learn how to program UDP broadcast socket programming in Java, as well as understand the basis for this whole laboratory work.



## General tasks of laboration-1
- [X] Choose your own port, so your program will not collide with other students programs on the same network.
- [X] Implement a Join message, that is sent from a client when the client starts.
- [X] When another client receives the Join message, it shall add the user to its list of active clients.
- [X] Implement a Leave message, that is sent from a client when the client leaves.
- [X] When another client receives the Leave message, it shall remove the user fromt its list of active clients.
- [X] Remember that the newly joined client should also get a list of all active client from the older clients.
- [X] Adjust the user interface according to your own taste.

## Run
This section demonstrates the obtained results when we run the program.

### Users Join
The below figure is a snapshot of the program when a new client joins the `Multicast network`.
![Users joined](/Images/twoUsers.PNG)

### Users Leave
The snapshot below demonstrates the state of the program when a client leaves the `Multicast network`.
![User left](/Images/userLeft.PNG)

## File Structur of Group Communication Program
```
.
├── distsys
│   ├── clients
│   │   ├── Client.java
│   │   └── UniqueIdentifier.java
│   ├── listeners
│   │   ├── ChatMessageListener.java
│   │   ├── JoinMessageListener.java
│   │   ├── LeaveMessageListener.java
│   │   └── ResponseJoinMessageListener.java
│   ├── messages
│   │   ├── ChatMessage.java
│   │   ├── JoinMessage.java
│   │   ├── LeaveMessage.java
│   │   ├── Message.java
│   │   ├── MessageSerializer.java
│   │   └── ResponseJoinMessage.java
│   └── GroupCommunication.java
├── Program.java
└── WindowProgram.java
```

## Components of Group Communication Program

A `class` for clients is created to maintain the clients properties. The class contain three primary variables. `ipAddress` and `port` are used to specify the `Multicast network's` IP address and respectivly the Port number, `ID` is a randomly generated unique identifier for the each client.

```java
public Client(InetAddress ipAddress, int port, int ID) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.ID = ID;
}
```

The following functions are responsible for prepareing the packages and sending them into the `Multicast network`.
In order to successfully prepare the `datagram package`. The program creates the message respective type, `serializes` it and then sends it to the appointed destination in the `Multicast network`.

```java
public void sendChatMessage(Client client, String chat);
public void sendJoinMessage(Client client);
public void sendResponseJoinMessage();
public void sendLeaveMessage();
```
Similarly, the received messages must be `deserialized` first in order to handle the messages. The `Multicast network` need then to identify the message type in order to call the apropriate method for handling the message. The `listeners` above handle messages respectivly and communicates the messages to the rest of the `Multicast network`.

```java
//Message Listeners
ChatMessageListener chatMessageListener = null;
JoinMessageListener joinMessageListener = null;
LeaveMessageListener leaveMessageListener = null;
ResponseJoinMessageListener responseJoinMessageListener = null;
```
The following method is an example of a received message operator. The method is responsible for showing the correct values in the program. Values are stored in a `vector` which then is used to avoid data replication with other messages.

```java
@Override
public void onIncomingJoinMessage(JoinMessage joinMessage) {
	try {
		gc.activeClientList.add(joinMessage.clientID);
		txtpnStatus.setText(joinMessage.clientID + " joined the conversation." + "\n" + txtpnStatus.getText());
		if(joinMessage.clientID != gc.activeClient.getID()){
			gc.sendResponseJoinMessage();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
```
## Credit
- Ayad Shaif (aysh1500)
- Patrik Högblom(pahg1600)