/* Copyright (C) 2019 Panagiotis Klironomos - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the GNU GENERAL PUBLIC LICENSE 
 */

package Server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws Exception{
        try {
            int port=5000; // port initialisation
            int counter=0;
            System.out.println("Server Started ....");
            ServerSocket ss= new ServerSocket(port);
            while(true){
                    counter++; 
                    // μετρητής των threads (Clients)
                    // thread(Clients) counter
                    Socket serverClient= ss.accept();
                    System.out.println(" >> " + "Client No:" + counter + " started!");
                    ServerClientThread sct = new ServerClientThread(serverClient,counter); 
                    sct.start();
                    
            }
            }catch(Exception e){
                    System.out.println(e);
            }
    }	
}

