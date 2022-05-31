package bubble.test.ex02;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BubbleFrame extends JFrame{

	private JLabel backgroundMap;
	private Player player;
	
	public BubbleFrame() {
		initObject();
		initSetting();
		setVisible(true);
	}
	
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		
		//JFrame>이미지
		setContentPane(backgroundMap);
		
		//JFrame>JPanel>이미지삽입
		//backgroundMap.setSize(1000,600);
		//add(backgroundMap);
		
		player = new Player();
		add(player); // setContentPane은 JPanel자체를 바꾸는 거지만 add는 JPanel에 그냥 덧붙이는 개념이다. 
	}
	
	private void initSetting() {
		setSize(1000, 640);
		setLayout(null); //absolute layout(자유롭게 그림을 그릴 수 있다.)
		setLocationRelativeTo(null); //JFrame창을 가운데 배치하기. 윈도우창 모니터 가운데 띄우기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌때 JVM도 같이 종료
	}
	
	public static void main(String[] args) {
		new BubbleFrame();
	}
}
