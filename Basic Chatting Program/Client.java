import java.io.*;
import java.util.*;
import java.net.*;

public class Client
{
    public static void main(String[] arg) throws Exception
    {
        Socket c_socket = new Socket(arg[0],8000);
        System.out.println("Connection Requested");

        DataInputStream in = new DataInputStream(c_socket.getInputStream());
        DataOutputStream out = new DataOutputStream(c_socket.getOutputStream());
        Scanner sc=new Scanner(System.in);
        System.out.println("\tType your message below.Click enter to send message.\n\tType Exit to end the connection");
        String s="";
        while(!s.equalsIgnoreCase("Exit"))
        {
            System.out.print("\n\t\tEnter your msg:");
            s=sc.nextLine();
            out.writeUTF(s);
            System.out.println("Server: " + in.readUTF());
        }
        
        in.close();
        out.close();
        c_socket.close();
    }
}
