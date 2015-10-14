//: c15:MultiJabberServer.java
// ������, ������� ���������� ���������������
// ��� ��������� ������ ����� ��������.
// {RunByHand}
import java.io.*;

import java.net.*;

class ServeOneJabber extends Thread {
   private Socket socket;
   private BufferedReader in;
   private PrintWriter out;
   
   public ServeOneJabber(Socket s) throws IOException {
      socket = s;
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      // �������� �������������� ������������:
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
            .getOutputStream())), true);
      // ���� ����� �� ��������������� ������� �������� �
      // ������������� ����������, �� ���������� �������� ��
      // �������� ������. � ��������� ������, ����
      // ������� ���.
      start(); // �������� run()
   }
   
   public void run() {
      try {
         while (true) {
            String str = in.readLine();
            if (str.equals("END"))
               break;
            System.out.println("Echoing: " + str);
            out.println(str);
         }
         System.out.println("closing...");
      }
      catch (IOException e) {
         System.err.println("IO Exception");
      }
      finally {
         try {
            socket.close();
         }
         catch (IOException e) {
            System.err.println("Socket not closed");
         }
      }
   }
}

public class MultiServer {
   static final int PORT = 9876;
   
   public static void main(String[] args) throws IOException {
      ServerSocket s = new ServerSocket(PORT);
      System.out.println("Server Started");
      try {
         while (true) {
            // ����������� �� ������������� ������ ����������:
            Socket socket = s.accept();
            try {
               new ServeOneJabber(socket);
            }
            catch (IOException e) {
               // ���� ���������� ��������, ����������� �����,
               // � ��������� ������, ���� ������� ���:
               socket.close();
            }
         }
      }
      finally {
         s.close();
      }
   }
} // /:~


//import java.io.File;
//public class MainServer {
//
//	public static void main(String[] args) {
//		initializeServer();
//		//36870
//		YWeatherParser.parse("36870");
//	}
//
//	static void initializeServer(){
//		File cachepath = new File("cache");
//		if(!(cachepath.exists() && cachepath.isDirectory())){
//			System.out.print("������� ������!");
//			cachepath.mkdirs();
//		}
//	}
//}
