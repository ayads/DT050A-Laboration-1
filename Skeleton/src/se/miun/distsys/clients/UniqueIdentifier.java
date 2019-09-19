package se.miun.distsys.clients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * A class that generate unique 
 * random numbers to be assigned 
 * to each connected clients.
 * */ 

public class UniqueIdentifier {

    private static List<Integer> clientId = new ArrayList<Integer>();
    private static final int RANGE = 10;
    private static int index = 0;

    static{
        for(int i = 0; i < RANGE; i++){
            clientId.add(i);
        }
        Collections.shuffle(clientId);
    }

    private UniqueIdentifier(){

    }

    public static int getUniqueIdentifier(){
        if(index > clientId.size() - 1){
            index = 0;
        }
        return clientId.get(index++);
    }
}