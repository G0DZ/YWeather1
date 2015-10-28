//http://javatutor.net/books/tiej/socket
import java.io.*;

import java.net.*;

public class MultiServer {
	static final int PORT = 9876;

	public static void main(String[] args) throws IOException {
		//запуск сервера
		ServerSocket s = new ServerSocket(PORT);
		InitCache.initializeServer();
		System.out.println("Server Started");
		try {
			while (true) {
				// СЕРВЕР Блокируется до возникновения нового соединения:
				Socket socket = s.accept();
				try {
					new OneServer(socket);
				} catch (IOException e) {
					// Если завершится неудачей, закрывается сокет,
					// в противном случае, нить закроет его:
					socket.close();
				}
			}
		} finally {
			s.close();
		}
	}
	private static class InitCache {
		private static void initializeServer() {
			try {
				//String pathToJar = new File(".").getAbsolutePath(); //мб
				File cachepath = new File("cache/");
				if (cachepath.exists())
					System.out.println("Каталог существует!");
				if (cachepath.isDirectory())
					System.out.println("Каталог - дирректория!");
				if (cachepath.mkdirs())
					System.out.println("Каталог создан!");

				else {
					System.out.println("Каталог отсутствует!");
				}
				System.out.println(cachepath.getAbsolutePath());
			} catch (Exception e) {
				// if any error occurs
				e.printStackTrace();
			}
		}
	}
}// /:~

class OneServer extends Thread {
	//серверные переменные
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	//интерфейс 
	private Weather factWeather;

	public OneServer(Socket s) throws IOException {
		socket = s;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// Включаем автоматическое выталкивание:
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream())), true);
		// Если любой из вышеприведенных вызовов приведет к
		// возникновению исключения, то вызывающий отвечает за
		// закрытие сокета. В противном случае, нить
		// закроет его.
		start(); // вызываем run()
	}

	public void run() {
		try {
			String str = in.readLine(); 		//получаем от клиента номер города
			//out.println(str);
			factWeather = YWeatherParser.getWeather(str); 	//получаем погоду города из интернета/кэша
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