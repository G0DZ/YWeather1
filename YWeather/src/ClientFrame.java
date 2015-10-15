import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientFrame extends JFrame {
	private static final long serialVersionUID = 5506524279338381258L;
	static JButton button_start;	//кнопка обновления погоды
	static JLabel TEST_WeatherText; //вывод погоды в текстовом виде
	static String button_start_txt = "Обновить";
	JPanel p1;
	public ClientFrame(){
		//поехали
		try { //стиль оформления
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		button_start = new JButton("Cursor.HAND_CURSOR"); //создаем кнопку.
		button_start.setText(button_start_txt);
		TEST_WeatherText = new JLabel("");
		
		//устанавливаем размер элементов
		TEST_WeatherText.setBounds(0, 0, 300, 100);
		button_start.setBounds(50, 105, 200, 30);
		
		//настраиваем действия на кнопки
		ClientFrameEngine cEngine = new ClientFrameEngine();
		button_start.addActionListener(cEngine);
		
		//добавляем элементы на панель
		p1 = new JPanel();
		p1.setLayout(null);
		p1.add(TEST_WeatherText);
		p1.add(button_start);
		getContentPane().add(p1);
		setPreferredSize(new Dimension(300, 165));
		
		this.pack(); 
		
		this.setTitle("YWeather");									//Заголовок окна
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	    //действие при закрытии
		this.setResizable(false);            						// Запрет на изменение размера экрана              
        //Точка размещения экрана
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int w = this.getWidth();
        int h = this.getHeight();
        center.x = center.x - w/2; 
        center.y = center.y - h/2;
        this.setLocation(center);
        this.setVisible(true);										// Отображаю окно 
	  }

	  public static void main(String[] args) { //эта функция может быть и в другом классе
	    new ClientFrame(); //Создаем экземпляр нашего приложения
	  }
}
