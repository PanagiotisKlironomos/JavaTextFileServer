/* Copyright (C) 2019 Panagiotis Klironomos - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the GNU GENERAL PUBLIC LICENSE 
 */

package Client;

import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) throws Exception {
        try{
            String request="";
            /* Δημιουργείται καινούριος client για κάθε αίτημα που ολοκληρώνεται
            έτσι προστέθηκε η δυνατότα εξόδου από αυτή την διαδικασία με το bye*/
            /* For every completed request, a new client is made so
            an exit option has been added from this process by using bye*/
            while(!request.equals("bye")){
                int port = 5000;
                Socket socket=new Socket("localhost",port);
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                Scanner myObj = new Scanner(System.in);
                String thisLine = null; 
                /*Αποστολή αιτήματος στον σέρβερ*/
                /*Sending request to the server*/
                System.out.println("Enter request: "); 
                request = myObj.nextLine();
                PrintWriter pr= new PrintWriter(socket.getOutputStream());
                pr.println(request);
                pr.flush();
                /*Λήψη απάντησης του σέρβερ*/
                /*getting server reply */
                InputStreamReader in= new InputStreamReader(socket.getInputStream());    
                BufferedReader bf= new BufferedReader(in);
                while ((thisLine = bf.readLine()) != null) {
                    System.out.println(thisLine);
                }
                socket.close();
            }

        }catch(Exception e){
          System.out.println(e);
        }
    }
}
