import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class YWeatherParser {
	private static Weather factWeather = new Weather();
	//private static Weather tommorowWeather = new Weather();
	
	public static Weather parse(String cityID) {
		//проверяем, есть ли файл, и не превышен ли порог времени хранения
		if(checkCache(cityID))
		{// если файл есть - загрузим информацию из него
			loadFile(cityID);
		}
		else{ //если файла нет или он "просрочен", идем в интернет
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
					ex.printStackTrace();
				}
			parseDoc(doc);
			writeFile(cityID);
			}
		System.out.println(factWeather.toString());
		return factWeather;
		}
		
		
	private static boolean checkCache(String cityID){
		File cachefile = new File("cache/"+cityID+".txt");
		if(cachefile.exists())
		{
			long a = System.currentTimeMillis();
			long b = cachefile.lastModified();
			if((a-b) > 1000*60*60) //один час в миллисекундах
			{
			//	System.out.print("Файл с таким именем НЕсуществует, GHBDTN!");
				return false;
			}
			//System.out.print("Файл с таким именем существует!");
			return true;	
		}
		//System.out.print("Файл с таким именем НЕсуществует!");
		return false;
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
        //System.out.print(factWeather.toString());
	}
	
	private static void writeFile(String cityID){
		File cachefile = new File("cache/"+cityID+".txt");
		try(FileWriter writer = new FileWriter(cachefile, false))
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
	
	private static void loadFile(String cityID){
		File cachefile = new File("cache/"+cityID+".txt");
		try(BufferedReader reader = new BufferedReader(new FileReader(cachefile)))
        {
			//разбор текстового файла 
        	String str = reader.readLine();
			factWeather.city = str.substring(7,str.length());	//city = TEST
			str = reader.readLine();
			factWeather.country = str.substring(10,str.length());	//country = TEST
			str = reader.readLine();
			factWeather.time = str.substring(7,str.length());	//time = TEST
			str = reader.readLine();
			factWeather.temperature = str.substring(14,str.length());	//temperature = TEST
			str = reader.readLine();
			factWeather.weatherType = str.substring(14,str.length());	//weatherType = TEST
			str = reader.readLine();
			factWeather.windDirection = str.substring(16,str.length());	//windDirection = TEST
			str = reader.readLine();
			factWeather.windSpeed = str.substring(12,str.length());	//windSpeed = TEST
			str = reader.readLine();
			factWeather.humidity = str.substring(11,str.length());	//humidity = TEST
			str = reader.readLine();
			factWeather.pressure = str.substring(12,str.length());	//pressure = TEST
			//System.out.println("HELLO\n\n"+factWeather.toString());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
	}
}
