import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server 
{
  static ArrayList <DataOutputStream> writer_list = new ArrayList < > ();
  static int num = 0;
  
  public static void main(String arg[]) throws Exception 
  {
    ServerSocket s = new ServerSocket(8010);
    System.out.println("Waiting for connection....");
    Socket socket;
    
    while (true) 
    {
      socket = s.accept();
      
      //Adding objext to list
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());
      writer_list.add(num, out);

      //assigning thread
      ConnectionHandler thread = new ConnectionHandler(socket, num);
      thread.start();
      num++;
    }
  }
  
  static void BroadCaster(String message, int num, String name) throws Exception 
  {
    DataOutputStream out;
    for (int i = 0; i < writer_list.size(); i++) 
    {
      out = writer_list.get(i);
      if (out == null)
        continue;
      if (i == num)
        out.writeUTF("✓✓");
      else
        out.writeUTF("\t\t" + name + ": " + message);
    }
    System.out.println(name + ": " + message + "\nSent to everyone.\n");
  }
}


class ConnectionHandler extends Thread 
{
  Socket socket;
  int num;
  
  ConnectionHandler(Socket sk, int n) 
  {
    socket = sk;
    num = n;
  }
  
  @Override
  public void run() 
  {
    try 
    {
      DataInputStream in = new DataInputStream(socket.getInputStream());
      String name = in.readUTF();
      System.out.println("New Connection:" + name + " ->" + socket.getInetAddress());
      String message;
      
      //reading message ,till end message
      while (!(message = in.readUTF()).equalsIgnoreCase("end")) 
      {
        Server.BroadCaster(message, num, name);
      }

      Server.writer_list.get(num).writeUTF("end");

      //closing resources and replacing null in closed object
      in.close();
      Server.writer_list.get(num).close();
      socket.close();

      Server.writer_list.set(num, null);
      System.out.println("\t>>" + name + " Disconnected");
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    } 
  }
}
