import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientFrame extends JFrame {
	private static final long serialVersionUID = 5506524279338381258L;
	//будем описывать используемые элементы сверху-вниз
	JLabel helloTextLabel;
	JLabel cityName;
	JLabel nowLabel;
	JLabel timeLabel;
	JPanel weatherSquare;
	JLabel weatherImage;
	JLabel temperatureLabel;
	JLabel weatherTypeText;

	static JButton button_start;	//кнопка обновления погоды
	JTextField textField;
	JTextArea TEST_WeatherText; //вывод погоды в текстовом виде
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

		//инициализация переменных
		helloTextLabel = new JLabel("Погода в городе: ");
		cityName = new JLabel();
		nowLabel = new JLabel("Cейчас: ");
		timeLabel = new JLabel();
		weatherSquare = new JPanel();
		weatherImage = new JLabel();
		temperatureLabel = new JLabel();
		weatherTypeText = new JLabel();

		button_start = new JButton("Cursor.HAND_CURSOR"); //создаем кнопку.
		button_start.setText(button_start_txt);
		textField = new JTextField("26063");
		TEST_WeatherText = new JTextArea("");
		TEST_WeatherText.setEnabled(false);
		TEST_WeatherText.setFont(new Font("Calibri", Font.PLAIN, 16));


		//устанавливаем размер элементов
		helloTextLabel.setBounds(10, 0, 145, 20);
		cityName.setBounds(147, 0, 403, 20);
		nowLabel.setBounds(10, 25, 65, 20);
		timeLabel.setBounds(68, 27, 50, 18);
		weatherSquare.setBounds(10, 50, 280, 90);
		weatherImage.setBounds(20, 60, 48, 48);
		temperatureLabel.setBounds(23, 110, 100, 30);
		weatherTypeText.setBounds(80, 65, 200, 30);

		TEST_WeatherText.setBounds(0, 90, 300, 250);
		textField.setBounds(50, 325, 200, 20);
		button_start.setBounds(50, 350, 200, 30);


		//настраиваем действия на кнопки
		ClientFrameEngine cEngine = new ClientFrameEngine(this);
		button_start.addActionListener(cEngine);

		//дополнительная настройка элементов (Шрифт и прочее)
		helloTextLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		cityName.setFont(new Font("Verdana", Font.BOLD, 14));
		nowLabel.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		timeLabel.setFont(new Font("TimesRoman", Font.BOLD, 12));
		weatherSquare.setBackground(new Color(255,248,205));
		temperatureLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		weatherTypeText.setFont(new Font("Verdana", Font.PLAIN, 12));

		//добавляем элементы на панель
		p1 = new JPanel();
		p1.setLayout(null);
		p1.add(helloTextLabel);
		p1.add(cityName);
		p1.add(nowLabel);
		p1.add(timeLabel);
		p1.add(weatherImage);
		p1.add(temperatureLabel);
		p1.add(weatherTypeText);
		p1.add(weatherSquare); //последний в списке

		//p1.add(TEST_WeatherText);
		p1.add(textField);
		p1.add(button_start);

		getContentPane().add(p1);
		setPreferredSize(new Dimension(550, 495));

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
