import java.net.*;
import java.io.*;
import java.util.*;

public class Client
{
  static boolean closeflag=false;
  public static void main(String[] arg) throws Exception 
  {
    Socket socket = new Socket(arg[0], 8010);
    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    Scanner sc = new Scanner(System.in);
    
    
    System.out.print("Enter your name: ");
    out.writeUTF(sc.nextLine());
    System.out.println("\nWelcome To Chat Room.\nType your message in the left side.\nType \"end\" to exit\n");
    System.out.println("You");
    
    //assigning thread for receiving message
    ReceiverThread t = new ReceiverThread(socket,in);
    t.start();
    
    //read message from user and sending to server till "end"
    String data = "";
    while (!(data.equalsIgnoreCase("end")))       
    {
      data = sc.nextLine();
      out.writeUTF(data);
    }
    System.out.println("Successfully disconnected");
    
    //closing resources
    if(closeflag)
    {
      in.close();
      out.close();
      socket.close();
    }
  }
}


class ReceiverThread extends Thread 
{
  private Socket socket;
  DataInputStream in ;
  
  ReceiverThread(Socket sk,DataInputStream in) 
  {
    socket = sk;
    this.in = in;
  }

  @Override
  public void run() 
  {
    try {
      String message = "";
      while (!(message = in.readUTF()).equalsIgnoreCase("End"))         
      {
        System.out.println(message);
      }
      Client.closeflag=true;
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}