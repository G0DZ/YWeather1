//http://javatutor.net/books/tiej/socket
import java.io.*;

import java.net.*;

public class MultiServer {
	static final int PORT = 9876;
   
	public static void main(String[] args) throws IOException {
		//������ �������
		ServerSocket s = new ServerSocket(PORT);
		InitCache.initializeServer();
		System.out.println("Server Started");
		try {
			while (true) {
				// ������ ����������� �� ������������� ������ ����������:
				Socket socket = s.accept();
				try {
					new OneServer(socket);
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
	
	private static class InitCache{
		private static void initializeServer(){
			File cachepath = new File("cache");
			if(!(cachepath.exists() && cachepath.isDirectory())){
				//System.out.print("������� ������!");
				cachepath.mkdirs();
			}
		}
	}
} // /:~

class OneServer extends Thread {
	//��������� ����������
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	//��������� 
	private Weather factWeather;
	
	public OneServer(Socket s) throws IOException {
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
			String str = in.readLine(); 		//�������� �� ������� ����� ������
        	//out.println(str);
        	factWeather = YWeatherParser.getWeather(str); 	//�������� ������ ������ �� ���������/���� 
        	if(factWeather != null)
        		out.println(factWeather.toString());
        	else
        		out.println("");
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