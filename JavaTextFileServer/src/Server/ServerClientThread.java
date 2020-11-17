/* Copyright (C) 2019 Panagiotis Klironomos - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the GNU GENERAL PUBLIC LICENSE 
 */

package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ServerClientThread extends Thread {
  Socket serverClient;
  int clientNo;
  int squre;
  ServerClientThread(Socket inSocket,int counter){
    serverClient = inSocket;
    clientNo=counter;
  }
  public void run(){
    try{
        /*Διάβασμα του αιτήματος του client*/
        /* client's Request reading*/
        InputStreamReader in= new InputStreamReader(serverClient.getInputStream());    
        BufferedReader bf= new BufferedReader(in);
        String str= "";
        boolean found=false;
        int exitflag=0;
        while(exitflag==0){
            /* Διάβασμα αιτήματος */
            /* Request reading */
            str= bf.readLine(); 
            /* Εκτύπωση αιτήματος (στον σέρβερ) */
            /* Printing request (to server) */
            System.out.println("Client's request: " + str); 
            /*Ορισμός Path */
            /*Path Definition */
            File folder = new File("C:\\Users");
            /*Δημιουργία πίνακα listOfFiles με τα αρχεία του path (φακέλου) */
            /*listOfFiles matrix creation with the files included in the given path (folder) */
            File[] listOfFiles = folder.listFiles();
            
            if (str.startsWith("get") ||str.startsWith("Get") || str.startsWith("GET") ) {                
                /*Δημιουργία string χωρίς το get (δηλαδή το όνομα του αρχείου) */
                /*string creation without get (requested file name) */
                String NameOfFile = str.substring(4, str.length()); 
                for (File listOfFile : listOfFiles) {
                    /*Αν υπάρχει το αρχείο*/
                    /*If the file exists*/
                    if (listOfFile.getName().equals(NameOfFile)) {
                        /*Φορτώνεται το txt περιεχόμενο του αρχείου σε ένα string
                        και στέλνεται στον client*/
                        /*The file's text content is loaded into a string
                        and it is sent to the server*/
                        found=true;
                        Scanner scan = new Scanner(listOfFile);
                        PrintWriter pr= new PrintWriter(serverClient.getOutputStream());
                        String fileDB= "OK\n";
                        while(scan.hasNextLine()){
                                fileDB=fileDB.concat(scan.nextLine() + "\n");
                        }
                        pr.println(fileDB);
                        pr.flush();
                        exitflag=1;
                    }                    
                }
                if (found == false){
                        PrintWriter pr= new PrintWriter(serverClient.getOutputStream());
                        String fileDB= "File "+ NameOfFile +" does not exist\n";
                        pr.println(fileDB);
                        pr.flush();
                        exitflag=1;
                }
            }            
            else if(str.equals("index") || str.equals("INDEX") || str.equals("Index")){  
                /*If the Client enters index the txt files of the folder are returned*/ 
                String fileNames = new String();
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        fileNames += listOfFile.getName();
                        fileNames += "\n";
                    }
                }
                PrintWriter pr= new PrintWriter(serverClient.getOutputStream());
                pr.println(fileNames);
                pr.flush();
                exitflag=2;
            }else if("bye".equals(str) || "Bye".equals(str) || "BYE".equals(str) ){
               PrintWriter pr= new PrintWriter(serverClient.getOutputStream());
               pr.println("Bye Client :) \n "
                    + "You have to run again the client file if you"
                       + " wish to connect with the server again ");
               pr.flush(); 
               exitflag=3;			   
            }else {
               PrintWriter pr= new PrintWriter(serverClient.getOutputStream());
               pr.println("ERROR! \n You have made a mistake");
               pr.flush(); 
               exitflag=4;			   
            }
        }      
        in.close();
        serverClient.close();
    }catch(Exception ex){
      System.out.println(ex);
    }finally{
      System.out.println("Client -" + clientNo + " exit!! ");
    }
  }
}


