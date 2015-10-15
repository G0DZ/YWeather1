import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
public class ClientFrameEngine implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton clickedButton =  (JButton) arg0.getSource();
    	// Если это кнопка "Ввод исходных данных"
    	String actioncommand = clickedButton.getActionCommand();
    	if (actioncommand == ClientFrame.button_start_txt) {
    		WeatherClient w = new WeatherClient();
    		w.syncConnect();
    	}
	}

}
