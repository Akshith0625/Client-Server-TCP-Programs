import java.net.*;
import java.io.*;

public class Server 
{
  public static void main(String[] args) 
  {
    try(ServerSocket s=new ServerSocket(8020))
    {
      s.setSoTimeout(180000);
      System.out.println("Waiting for connection...");
      while(true)
      {
        Socket socket=s.accept();
        System.out.println("New Client Connection ->"+socket.getInetAddress());
      
        //assigning thread
        ConnectionHandler t=new ConnectionHandler(socket);
        t.start();
      }
    }
    catch(SocketTimeoutException ex)
    {
      System.out.println("Server waiting time is over.Server will be closed");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }  
}

class ConnectionHandler extends Thread
{
  private Socket temp_socket;
  ConnectionHandler(Socket sk)
  {
    temp_socket=sk;
  }
  @Override
  public void run()
  {
    try
    (Socket socket=temp_socket;
    DataInputStream in=new DataInputStream(socket.getInputStream());
    DataOutputStream sharer=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())))
    {
      //Getting the requested file and checking its existence
      String filename=in.readUTF();      
      File f_obj=new File(filename);
      if(f_obj.exists())
      {
        sharer.writeUTF("Start");
        String msg="Requested File:"+filename+" is found. File sharing in process...";
        System.out.println(msg);
        sharer.writeUTF(msg);        
        sharer.flush();
        
        //Sharing the file data
        BufferedInputStream file_reader=new BufferedInputStream(new FileInputStream(f_obj));
        int data;
        while((data=file_reader.read())!=-1)
        {
          sharer.write(data);
        }
        file_reader.close();
        sharer.flush();
        
        //receipt of file transfer
        msg="File Transferred Successfully";
        System.out.println(msg+"\nFile size sent:"+f_obj.length()+"Bytes");                
      }       
      
      else
      {
        sharer.writeUTF("Stop");
        String msg="The Requested File:"+filename+" is not found";
        System.out.println(msg);
        sharer.writeUTF(msg);        
      }
    }    
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}
