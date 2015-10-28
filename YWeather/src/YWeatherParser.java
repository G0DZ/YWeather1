import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class YWeatherParser {
	private static Weather factWeather = new Weather();
	//private static Weather tommorowWeather = new Weather();

	public static Weather getWeather(String cityID) {
		//проверяем, есть ли файл, и не превышен ли порог времени хранения
		System.out.print("Попытка получить погоду с сервера. Проверка на наличие кэша...");
		if(checkCache(cityID))
		{// если файл есть - загрузим информацию из него
			System.out.println(" Успешно.\nЗагрузка информации из кэша...");
			loadFile(cityID);
		}
		else{ //если файла нет или он "просрочен", идем в интернет
			System.out.println(" Неудача. Кэш отсутствует.");
			Document doc = null;
			try {
				URL url = new URL("http://export.yandex.ru/weather-ng/forecasts/"
						+ cityID + ".xml");
				URLConnection uc = url.openConnection();
				InputStream is = uc.getInputStream();//создали поток
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				doc = db.parse(is);//непосредственно парсинг
				doc.getDocumentElement().normalize();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"Ошибка при запросе к Яндекс АПИ");
				return null;
				//ex.printStackTrace();
			}
			parseDoc(doc);
			writeFile(cityID);
		}
		System.out.println(factWeather.toString());
		return factWeather;
	}

	//получаем погоду, разбирая массив строк
	//единый интерфейс, используется при разборе текстового файла и ответа, приходящего клиенту
	//потому имеет видимость public
	public static Weather parseAnswer(ArrayList<String> list){
		String str = null;
		Weather w = new Weather();
		str = list.get(0);
		w.city = str.substring(5,str.length());	//city = TEST
		str = list.get(1);
		w.country = str.substring(8,str.length());	//country = TEST
		str = list.get(2);
		w.time = str.substring(5,str.length());	//time = TEST
		str = list.get(3);
		w.temperature = str.substring(12,str.length());	//temperature = TEST
		str = list.get(4);
		w.weatherType = str.substring(12,str.length());	//weatherType = TEST
		str = list.get(5);
		w.windDirection = str.substring(14,str.length());	//windDirection = TEST
		str = list.get(6);
		w.windSpeed = str.substring(10,str.length());	//windSpeed = TEST
		str = list.get(7);
		w.humidity = str.substring(9,str.length());	//humidity = TEST
		str = list.get(8);
		w.pressure = str.substring(9,str.length());	//pressure = TEST
		str = list.get(9);
		w.imageName = str.substring(10,str.length());//imageName=TEST
		//System.out.println("HELLO\n\n"+w.toString());
		return w;
	}

	private static boolean checkCache(String cityID){
		File cachefile = new File("cache/"+cityID+".txt");
		if(cachefile.exists())
		{
			long a = System.currentTimeMillis();
			long b = cachefile.lastModified();
			if((a-b) > 1000*60*60) //один час в миллисекундах
			{
				System.out.print("Файл устарел, GHBDTN!");
				return false;
			}
			System.out.print("Файл с таким именем существует!");
			return true;
		}
		System.out.print("Файл с таким именем НЕ существует!");
		return false;
	}

	public static ArrayList<String> splitAnswer(String str){
		//str = str.replace("","");
		StringTokenizer stk = new StringTokenizer(str,"$"); //логические отрезки разделены запятыми
		int z = stk.countTokens(); //количество разделителей в строке
		ArrayList<String> list = new ArrayList<>();
		//System.out.println("Тестирование метода parseAnswer\n");
		for(int i = 0; i<z; i++)
		{
			list.add(stk.nextToken());
			//System.out.println(list.get(i));
		}
		return list;
	}

	private static void parseDoc(Document doc){
		//если есть интернет и запросы верны - аттрибуты не будут пусты.
		NamedNodeMap attr = doc.getElementsByTagName("forecast").item(0).getAttributes();
		//из этого родителя нам и нужно содержимое
		factWeather.city = attr.getNamedItem("city").getNodeValue();
		factWeather.country = attr.getNamedItem("country").getNodeValue();
		//
		NodeList nl = doc.getElementsByTagName("fact").item(0).getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nNode = nl.item(i);
			//строковый свитч
			switch(nNode.getNodeName())
			{
				case "observation_time":
					factWeather.time = nNode.getTextContent();
					break;
				case "temperature":
					factWeather.temperature = nNode.getTextContent();
					break;
				case "weather_type":
					factWeather.weatherType = nNode.getTextContent();
					break;
				case "wind_direction":
					factWeather.windDirection = nNode.getTextContent();
					break;
				case "wind_speed":
					factWeather.windSpeed = nNode.getTextContent();
					break;
				case "pressure":
					factWeather.pressure = nNode.getTextContent();
					break;
				case "humidity":
					factWeather.humidity = nNode.getTextContent();
					break;
				case "image-v3":
					factWeather.imageName = nNode.getTextContent();
					break;
			}
		}
		System.out.println("Погода загружена из интернета.");
		System.out.print(factWeather.toString());
	}

	private static void writeFile(String cityID){
		File cachefile = new File("cache/"+cityID+".txt");
		try(FileWriter writer = new FileWriter(cachefile, false))
		{
			writer.write("city=" + factWeather.city+"\n");
			writer.append("country=" + factWeather.country+"\n");
			writer.append("time=" + factWeather.time + "\n");
			writer.append("temperature=" + factWeather.temperature+"\n");
			writer.append("weatherType=" + factWeather.weatherType+"\n");
			writer.append("windDirection=" + factWeather.windDirection+"\n");
			writer.append("windSpeed=" + factWeather.windSpeed+"\n");
			writer.append("humidity=" + factWeather.humidity+"\n");
			writer.append("pressure=" + factWeather.pressure+"\n");
			writer.append("imageName=" + factWeather.imageName+"\n");
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
		}
	}

	private static void loadFile(String cityID){
		File cachefile = new File("cache/"+cityID+".txt");
		try(BufferedReader reader = new BufferedReader(new FileReader(cachefile)))
		{
			System.out.println("Обнаружен файл. Попытка чтения");
			ArrayList<String> list = new ArrayList<>(); //arraylist для хранения содержимого файла
			String str = null; //строка, используемая для записи
			while((str=reader.readLine())!=null)
			{
				//str = str.replace(" ", "");
				list.add(str);
				System.out.println(str);
			}
			//получаем погоду, разбирая массив строк
			factWeather = YWeatherParser.parseAnswer(list);
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
		}
	}
}