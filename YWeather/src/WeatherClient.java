import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WeatherClient {
	private Weather clientWeather;
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
			      // Вывод автоматически Output быталкивается PrintWriter'ом.
			      PrintWriter out = new PrintWriter(new BufferedWriter(
			            new OutputStreamWriter(socket.getOutputStream())), true);
			      out.println("26063");
			      String str = in.readLine();
			      splitAnswer(str);
			      System.out.println(str);
			   }
			   finally {
			      System.out.println("closing...");
			      socket.close();
			   }
		}
		catch (IOException e){}
	}
	
	public Weather getClientWeather() {
		return clientWeather;
	}
	
	//разбивает строку ответа, преобразуя ее в массив строк
	//а затем парсит в формат "Weather"
	//ответ имеет стандартую структуру, описанную в методе Weather.toString();
	private void splitAnswer(String str){
		StringTokenizer stk = new StringTokenizer(str,","); //логические отрезки разделены запятыми	    
	    int z = stk.countTokens();
	    System.out.println(z);
	    ArrayList<String> list = new ArrayList<String>();
	    System.out.println("Тестирование метода parseAnswer\n");
	    for(int i = 0; i<z; i++)
	    {
	    	list.add(stk.nextToken());
	    	//System.out.println(list.get(i));
	    }
	    //получаем погоду, разбирая массив строк
		clientWeather = YWeatherParser.parseAnswer(list);
	    
		
	}
}