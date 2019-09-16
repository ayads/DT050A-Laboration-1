package se.miun.distsys.clients;

import java.net.InetAddress;

/**
 * A class that defines each 
 * client in the network.
 */

public class Client {
    public String name;
    public InetAddress ip;
    public int port;
    private final int ID;
    public boolean status;

    public Client(String name, InetAddress ip, int port, int ID, boolean status) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.ID = ID;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public boolean getStatus(){
        return status;
    }
}