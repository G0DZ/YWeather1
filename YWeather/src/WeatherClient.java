import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class WeatherClient {
	private Weather ClientWeather;
	public void syncConnect()
	{
		try{
			InetAddress addr = InetAddress.getByName("localhost");
			System.out.println("addr = " + addr);
			Socket socket = new Socket(addr, MultiServer.PORT);
			try {
			      System.out.println("socket = " + socket);
			      BufferedReader in = new BufferedReader(new InputStreamReader(socket
			            .getInputStream()));
			      // ����� ������������� Output ������������� PrintWriter'��.
			      PrintWriter out = new PrintWriter(new BufferedWriter(
			            new OutputStreamWriter(socket.getOutputStream())), true);
			      out.println("26063");
			      String str = in.readLine();
			      //System.out.println(str);
			   }
			   finally {
			      System.out.println("closing...");
			      socket.close();
			   }
		}
		catch (IOException e){}
	}
	
	public Weather getClientWeather() {
		return ClientWeather;
	}
}