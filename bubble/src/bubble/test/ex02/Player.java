package bubble.test.ex02;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Player extends JLabel{
	
	private int x;
	private int y;
	
	private ImageIcon playerR,playerL; //player의 오른편 이미지, 왼편 이미지
	
	public Player() {
		initObject(); //player의 이미지 삽입
		initSetting(); //player의 초기위치 설정
		
	}
	
	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}
	private void initSetting() {
		//player의 초기위치 설정
		x = 55;
		y = 535;
		
		//Player자체가 JLable이라서 그냥 PlayerR을 넣으면 됨.
		setIcon(playerR); //"this"생략 가능. this.setIcon(PlayerR);
		setSize(50, 50);
		setLocation(x, y);
	}
}
