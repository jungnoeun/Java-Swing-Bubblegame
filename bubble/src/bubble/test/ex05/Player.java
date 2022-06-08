package bubble.test.ex05;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

// class Player -> new 가능한 애들!! 게임에 존재할 수 있음.(추상메서드를 가질 수 없다.)
@Getter
@Setter //boolean들은 isLeft(),isRight()같은 함수가 만들어진다. 나머지는 get-,set-이 붙는다.
public class Player extends JLabel implements Moveable{
	
	//위치상태
	private int x;
	private int y;
	
	//움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	//플레이어 속도 상태
	private final int SPEED = 3;
	private final int JUMPSPEED = 2; // up & down
	
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
		
		left = false;
		right = false;
		up = false;
		down = false;
		
		//Player자체가 JLable이라서 그냥 PlayerR을 넣으면 됨.
		setIcon(playerR); //"this"생략 가능. this.setIcon(PlayerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	@Override
	public void left() {
		System.out.println("left");
		left = true;
		new Thread(()-> {
			while(left) {
				setIcon(playerL);
				x = x-SPEED;
				setLocation(x, y);	
				try {
					Thread.sleep(10); //0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	@Override
	public void right() {
		System.out.println("right");
		right = true;
		new Thread(()-> {
			while(right) {
				setIcon(playerR);
				x = x+SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); //0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	//left + up, right + up
	@Override
	public void up() {
		System.out.println("up");
		up = true;
		new Thread(() -> {
			for(int i=0;i<130/JUMPSPEED;i++) { //for문은 상승하는 과정이다.
				y = y-JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			up = false; //up은 누른다고 계속 올라가는 것이 아니다.
			down();
		}).start();
	}

	@Override
	public void down() {
		System.out.println("down");
		down = true;
		new Thread(()->{
			for(int i=0;i<130/JUMPSPEED;i++) { //for문은 하강하는 과정이다.
				y = y+JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			down = false; //더이상 하강하지 않으므로 false로 표시를 해준다.
			
		}).start();
	}
}
