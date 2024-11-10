import java.io.*;
import java.net.*;
import java.util.*;

//MultiClient Handling
public class Client {
    
    public static void main(String[] arg)throws Exception
    {
        Socket socket=new Socket(arg[0],8000);
        DataInputStream in=new DataInputStream(socket.getInputStream());
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        Scanner sc=new Scanner(System.in);
        
        System.out.print("Enter the msg you need to send:");
        String s=sc.nextLine();
        out.writeUTF(s);
        System.out.print("Server message: "+in.readUTF());
        
        in.close();
        out.close();
        socket.close();
    }
    
}
