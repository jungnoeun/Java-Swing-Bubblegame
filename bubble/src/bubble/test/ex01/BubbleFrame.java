package bubble.test.ex01;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;

//1. 윈도우 창이 되었음
//2. 윈도우 창은 내부에 패널을 하나 가지고 있음
public class BubbleFrame extends JFrame{
	private JTextField txtText;

	public BubbleFrame() {
		setSize(1000, 640);
		getContentPane().setLayout(null); //absolute layout로의 변경으로 인해 생김. 원하는 위치에 컴포넌트 놓기가능
		
		//버튼 생성
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(129, 135, 91, 23);
		getContentPane().add(btnNewButton);
		
		//텍스트 생성
		txtText = new JTextField();
		txtText.setText("text지롱");
		txtText.setBounds(284, 212, 96, 21);
		getContentPane().add(txtText);
		txtText.setColumns(10);
		
		setVisible(true); //그림을 그려라 = 창을 보이게 해줌
	}
	
	public static void main(String[] args) {
		new BubbleFrame();
	}
}
