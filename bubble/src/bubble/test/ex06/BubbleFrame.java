package bubble.test.ex06;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BubbleFrame extends JFrame{

	private JLabel backgroundMap;
	private Player player; //player객체는 계속 틀어지고 있음(while의 개념)
	
	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
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
	
	//캐릭터 좌우이동
	private void initListener() {
		addKeyListener(new KeyAdapter() {
			
			//키보드 클릭 이벤트
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				
				switch(e.getKeyCode()) { //VK_LEFT: 37, VK_RIGHT: 39, VK_UP: 38, VK_DOWN: 40
				case KeyEvent.VK_LEFT: //key의 left를 누르면 Player의 left메서드를 호출해서 x좌표를 변경하는 것이 목적
					if(!player.isLeft()) {
						player.left();	
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(!player.isRight()) {
						player.right();	
					}
					break;
				case KeyEvent.VK_UP:
					if(!player.isUp() && !player.isDown()) { 
						player.up();	
					}
					break;
				//보글보글 게임에서 키를 눌러서 캐릭터가 아래로 움직이는 모션은 없음 -> down역할은 필요x
				/*case KeyEvent.VK_DOWN:
					player.down();
					break;*/
				}
			}
			
			
			//키보드 해제 이번트 핸들러
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				}
			}
			
		});
	}
	
	public static void main(String[] args) {
		new BubbleFrame();
	}
}
