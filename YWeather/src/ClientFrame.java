import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

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
	static JButton button_start;	//������ ���������� ������
	JTextField textField; 
	JTextArea TEST_WeatherText; //����� ������ � ��������� ����
	static String button_start_txt = "��������";
	JPanel p1;
	public ClientFrame(){
		//�������
		try { //����� ����������
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		button_start = new JButton("Cursor.HAND_CURSOR"); //������� ������.
		button_start.setText(button_start_txt);
		textField = new JTextField();
		TEST_WeatherText = new JTextArea("");
		TEST_WeatherText.setEnabled(false);
		TEST_WeatherText.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		//������������� ������ ���������
		TEST_WeatherText.setBounds(0, 0, 300, 300);
		textField.setBounds(50, 305, 200, 20);
		button_start.setBounds(50, 330, 200, 30);
		
		//����������� �������� �� ������
		ClientFrameEngine cEngine = new ClientFrameEngine(this);
		button_start.addActionListener(cEngine);
		
		//��������� �������� �� ������
		p1 = new JPanel();
		p1.setLayout(null);
		p1.add(TEST_WeatherText);
		p1.add(textField);
		p1.add(button_start);
		getContentPane().add(p1);
		setPreferredSize(new Dimension(300, 395));
		
		this.pack(); 
		
		this.setTitle("YWeather");									//��������� ����
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	    //�������� ��� ��������
		this.setResizable(false);            						// ������ �� ��������� ������� ������              
        //����� ���������� ������
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int w = this.getWidth();
        int h = this.getHeight();
        center.x = center.x - w/2; 
        center.y = center.y - h/2;
        this.setLocation(center);
        this.setVisible(true);										// ��������� ���� 
	  }

	  public static void main(String[] args) { //��� ������� ����� ���� � � ������ ������
	    new ClientFrame(); //������� ��������� ������ ����������
	  }
}
