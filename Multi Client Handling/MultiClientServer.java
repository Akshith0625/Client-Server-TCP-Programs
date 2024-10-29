import java.net.*;
import java.io.*;

public class MultiClientServer
{
public static void main(String arg[]) throws Exception
    {
        
        ServerSocket s= new ServerSocket(8000);
        Socket socket;
        int num=0;
        s.setSoTimeout(100000);
        try
        {
            System.out.println("Waiting for Client connection...");
            while(true)
            {
                socket=s.accept();
                num++;
                System.out.println("New Connection "+socket.getInetAddress()+".Client "+num);
                
                //assigning thread
                ConnectionHandler thread=new ConnectionHandler(socket,num);
                thread.start();
            }
        }
        catch(SocketTimeoutException e)
        {
            System.out.println("Time limit exceeded no new client connection or no response");
            s.close();
        }
    }
}

class ConnectionHandler extends Thread
{
    private Socket socket;
    int num;
    ConnectionHandler(Socket sk,int n)
    {
        socket=sk;
        num=n;
    }
    
    public void run()
    { 
        try
        {
            DataInputStream in=new DataInputStream(socket.getInputStream());
            DataOutputStream out=new DataOutputStream(socket.getOutputStream());
     
            System.out.println("Client "+num+" message: "+in.readUTF());                               
            out.writeUTF("Hello client your message has been recieved");
            
            in.close();
            out.close();
            socket.close();
            System.out.println("Client "+num+" disconnected");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
