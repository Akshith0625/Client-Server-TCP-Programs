import java.io.*;
import java.net.*;

//Single Message sending Program 
public class Client 
{
    public static void main(String[] args) throws Exception 
    {
        Socket c_socket = new Socket("127.0.0.1", 8000);
        System.out.println("Connection Requested");

        DataInputStream in = new DataInputStream(c_socket.getInputStream());
        DataOutputStream out = new DataOutputStream(c_socket.getOutputStream());

        out.writeUTF("Hi server!Send me today's date");

        System.out.println("Service requested");
        System.out.println("Server message: " + in.readUTF());

        in.close();
        out.close();
        c_socket.close();
    }
}
