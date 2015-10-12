import java.io.File;
public class MainServer {

	public static void main(String[] args) {
		initializeServer();
		//36870
		YWeatherParser.parse("36870");
	}

	static void initializeServer(){
		File cachepath = new File("cache");
		if(!(cachepath.exists() && cachepath.isDirectory())){
			System.out.print("Каталог создан!");
			cachepath.mkdirs();
		}
	}
}
