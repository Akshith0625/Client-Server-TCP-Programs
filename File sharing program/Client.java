import java.net.*;
import java.io.*;
import java.util.*;

public class Client 
{
  public static void main(String[] args) 
  {
    try(Socket socket=new Socket(args[0],8020);    
    DataInputStream sharer=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    DataOutputStream out=new DataOutputStream(socket.getOutputStream()))
    {
      Scanner sc=new Scanner(System.in);
      System.out.print("Enter the file you need(with file extension): ");
      String filename=sc.nextLine();
      out.writeUTF(filename);
      out.flush();
      
      String signal=sharer.readUTF();            
      if(signal.equalsIgnoreCase("Start"))
      {
        System.out.println(sharer.readUTF());
        //Creating new File to store data
        File f_obj=new File("Received_"+filename);
        f_obj.createNewFile();
        BufferedOutputStream writer=new BufferedOutputStream(new FileOutputStream(f_obj));
        
        //Receiving the file and writing to device 
        int data;
        while((data=sharer.read())!=-1)
        {
          writer.write(data);
        }
        writer.close();
        System.out.println("\nFile Received Successfully \nFile size: "+f_obj.length()+"Bytes\nFile Location: "+f_obj.getAbsolutePath());
      }
      else
      {
        System.out.println(sharer.readUTF());
        System.out.println("Recheck the filename and try again later");
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
}
