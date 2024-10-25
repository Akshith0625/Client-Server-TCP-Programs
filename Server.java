import java.io.*;
import java.lang.*;
import java.util.*;
import java.net.*;

public class Server 
{
    public static void main(String[] args) throws Exception 
    {
        ServerSocket s = new ServerSocket(8000);
        System.out.println("Waiting for client connection");

        Socket s_socket = s.accept();
        System.out.println("Client connected");

        DataInputStream in = new DataInputStream(s_socket.getInputStream());
        DataOutputStream out = new DataOutputStream(s_socket.getOutputStream());

        System.out.println("Client message:" + in.readUTF());

        //Calculating date and sending to client 
        Calendar obj = Calendar.getInstance();
        String date = obj.get(Calendar.DATE) + "/" + (obj.get(Calendar.MONTH) + 1) + "/" + obj.get(Calendar.YEAR);
        out.writeUTF("Hi Akshith \nToday's date is- " + date);

        s.close();
        s_socket.close();
        in.close();
        out.close();
    }
}