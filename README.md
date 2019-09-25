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
![Users joined](/Images/twoUsers.PNG)

### Users Leave
![User left](/Images/userLeft.PNG)

## Credit
- Ayad Shaif (aysh1500)
- Patrik Högblom(pahg1600)

## 

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

```java
public void sendChatMessage(Client client, String chat);
public void sendJoinMessage(Client client);
public void sendResponseJoinMessage();
public void sendLeaveMessage();
```

```java
//Message Listeners
ChatMessageListener chatMessageListener = null;
JoinMessageListener joinMessageListener = null;
LeaveMessageListener leaveMessageListener = null;
ResponseJoinMessageListener responseJoinMessageListener = null;
```

```java
//Setters for handling each listener
public void setChatMessageListener(ChatMessageListener listener);
public void setJoinMessageListener(JoinMessageListener listener);
public void setResponseJoinMessageListener(ResponseJoinMessageListener listener);
public void setLeaveMessageListener(LeaveMessageListener listener);
```