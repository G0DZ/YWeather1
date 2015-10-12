import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class YWeatherParser {
	static Weather factWeather = new Weather();
	static Weather tommorowWeather = new Weather();
	
	public static void parse(String cityID) {
		//if(checkCache(cityID))
		//{
			//
		//}
		//else{
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
					//JOptionPane.showMessageDialog(null,
						//	"Ошибка при запросе к Яндекс АПИ");
					ex.printStackTrace();
				}
			parseDoc(doc);
			writeFile(cityID);
		//	}
		}
		
		
	private static boolean checkCache(String cityID){
		File cachefile = new File("cache/"+cityID+".txt");
		if(cachefile.exists())
		{
			System.out.print("Файл с таким именем существует!");
			return true;	
		}
		else
		{
			System.out.print("Файл с таким именем НЕсуществует!");
			try {
				cachefile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
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
            }
        }
        System.out.print(factWeather.toString());
	}
	
	private static void writeFile(String cityID){
		try(FileWriter writer = new FileWriter("cache/"+cityID+".txt", false))
        {
            writer.write("city = " + factWeather.city+"\n");
            writer.append("country = " + factWeather.country+"\n");
            writer.append("time = " + factWeather.time+"\n");
            writer.append("temperature = " + factWeather.temperature+"\n");
            writer.append("weatherType = " + factWeather.weatherType+"\n");
            writer.append("windDirection = " + factWeather.windDirection+"\n");
            writer.append("windSpeed = " + factWeather.windSpeed+"\n");
            writer.append("humidity = " + factWeather.humidity+"\n");
            writer.append("pressure = " + factWeather.pressure+"\n");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
	}
}
