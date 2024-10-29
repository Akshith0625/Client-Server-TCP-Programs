import java.io.*;
import java.lang.*;
import java.util.*;
import java.net.*;

public class Server
{
    public static void main(String[] args) throws Exception 
    {
        ServerSocket s= new ServerSocket(8000);
        System.out.println("Waiting for client connection");

        Socket s_socket = s.accept();
        System.out.println("Client connected:"+s_socket.getLocalAddress());

        DataInputStream in = new DataInputStream(s_socket.getInputStream());
        DataOutputStream out = new DataOutputStream(s_socket.getOutputStream());
        Scanner sc=new Scanner(System.in);
        
        String msg,reply;
        while(!(msg=in.readUTF()).equalsIgnoreCase("Exit"))
        {
            System.out.println("\nClient :" + msg);
            System.out.print("\t\tEnter your msg: ");  
            reply=sc.nextLine();
            out.writeUTF(reply);
        }
        msg="\tCommunication ended";
        out.writeUTF(msg);
	System.out.println(msg);
        in.close();
        out.close();
        s.close();
        s_socket.close();
    }
}
