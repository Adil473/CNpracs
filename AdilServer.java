import java.io.*;
import java.net.*;
// public class AdilServer{
//     public static void main(String []args){
//         try{
//             ServerSocket ss = new ServerSocket(6666);
//             Socket s = ss.accept();
//             DataInputStream dis = new DataInputStream(s.getInputStream());
//             String str = (String)dis.readUTF();
//             int x = Integer.parseInt(str);
//             System.out.println("Server side reponse: ");
//             System.out.println("The cube of the number "+x+" is "+x*x*x);
//             ss.close();
//         }
//         catch(Exception e){
//             System.out.println(e);
//         }
//     }
// }

class AdilServer{
    public static void main(String []args) throws Exception{
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("Staring server..");
        Socket s = ss.accept();
        System.out.println("Client accepted");
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        String str = (String)din.readUTF();
        int x = Integer.parseInt(str);
        System.out.println("Server Side Response: ");
        dout.writeUTF("The cube is " + x*x*x);
        din.close();
        System.out.println("Closing Connection");
        s.close();
        ss.close();


    }
}