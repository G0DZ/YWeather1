import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ClientFrameEngine implements ActionListener{
	private ClientFrame parent;
	private Weather clientWeather;
	private String cityID;

	public ClientFrameEngine(ClientFrame parent) {
		this.parent = parent;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton clickedButton =  (JButton) arg0.getSource();
		// Если это кнопка "Ввод исходных данных"
		String actioncommand = clickedButton.getActionCommand();
		if (actioncommand.equals(ClientFrame.button_start.getText())){
			cityID = parent.textField.getText();
			if(!cityID.equals(""))
			{
				syncConnect();
				if(clientWeather!= null)
				{
					ArrayList<String> list = YWeatherParser.splitAnswer(clientWeather.toString());
					StringBuilder str = new StringBuilder("");
					for(int i = 0; i < list.size(); i++)
					{
						str.append(list.get(i));
						str.append("\n");
					}
					//присвоение значений элементов форм
					//согласно полученным полям
					parent.TEST_WeatherText.setText(str.toString());
					parent.cityName.setText(clientWeather.city+", "+clientWeather.country);
					parent.timeLabel.setText(new Time(System.currentTimeMillis()).toString());
					parent.weatherImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/"+
							clientWeather.imageName+".png")));
					if(Integer.parseInt(clientWeather.temperature)>0)
						parent.temperatureLabel.setText("+"+clientWeather.temperature+" \u00b0"+"C");
					else
						parent.temperatureLabel.setText(clientWeather.temperature+" \u00b0"+"C");
					parent.weatherTypeText.setText(clientWeather.weatherType);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Строка ввода города пуста!");
			}
		}
	}

	public void syncConnect()
	{
		try{
			InetAddress addr = InetAddress.getByName("localhost");
			System.out.println("Connection address = " + addr);
			System.out.print("Trying to connect..");
			try(Socket socket = new Socket(addr, MultiServer.PORT)) {
				System.out.println(" Success!");
				System.out.println("Socket info = " + socket);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));
				// Вывод автоматически Output выталкивается PrintWriter'ом.
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);

				out.println(cityID);
				//out.println("26063"); //передача серверу номера запрашиваемого города (номера на яндексе)
				System.out.print("Trying to getting answer...");
				String str = in.readLine();
				if(!str.equals(""))
				{
					System.out.println("Success!");
					//разбиваем полученый ответ на массив строк
					ArrayList<String> list = YWeatherParser.splitAnswer(str);
					//получаем погоду, разбирая массив строк
					clientWeather = YWeatherParser.parseAnswer(list);
					//System.out.println(str);
					System.out.println("Result: "+clientWeather.toString());
				}
				else
					System.out.println("Failed!");
				System.out.println("Closing socket...");
			}
			catch (Exception e){
				System.out.println(" Failed!");
			}
		}
		catch (IOException e){}
	}


}