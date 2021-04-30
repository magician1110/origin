package tt;
 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
 
public class ab {
     
     
 
    public static void main(String[] args) throws IOException {
 
    	Socket clientSocket = new Socket("10.10.11.16", 5223);
        File inputFolder = new File("C:\\DiffCounterTemp\\");        
        traverse(inputFolder,clientSocket);
 
    }
 
    public static void traverse(File parentNode,Socket clientSocket) throws IOException {
 
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
        if (parentNode.isDirectory()) {
            File childNodes[] = parentNode.listFiles();
            for (File childNode : childNodes) {
                if (childNode.isFile()) {
                    try {                     
                            File f = new File(childNode.toString());
                            String s1 = childNode.getName();
                            System.out.println(s1);
                            dos.writeUTF(s1);                                                                               
                            DataInputStream bis = new DataInputStream(new FileInputStream(f));
                            int bs = 10000;
                            byte[] a = new byte[bs];
                            int j=0;
                            while ((j = bis.read(a, 0, bs))!= -1){                              
                               System.out.println(j);
                                dos.write(a, 0, j);                              
                            }
                           System.out.println(j);
                            
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    }                              
                traverse(childNode,clientSocket);
            }
        }
        
    }
}