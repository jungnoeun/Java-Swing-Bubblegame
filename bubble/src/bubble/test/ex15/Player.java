package bubble.test.ex15;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

// class Player -> new 가능한 애들!! 게임에 존재할 수 있음.(추상메서드를 가질 수 없다.)
@Getter
@Setter //boolean들은 isLeft(),isRight()같은 함수가 만들어진다. 나머지는 get-,set-이 붙는다.
public class Player extends JLabel implements Moveable{
	
	private BubbleFrame mContext;
	
	//위치상태
	private int x;
	private int y;
	
	//플레이어의 방향 - 움직임 상태는 플레이어가 움직일때만, 즉, 키를 눌렀을때만 감지한다. 그래서 가만히 있을때의 방향도 감지해야 한다.
	private PlayerWay playerWay;
	
	//움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	//벽에 충돌한 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;
	
	//플레이어 속도 상태
	private final int SPEED = 4;
	private final int JUMPSPEED = 2; // up & down
	
	private ImageIcon playerR,playerL; //player의 오른편 이미지, 왼편 이미지
	
	public Player(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject(); //player의 이미지 삽입
		initSetting(); //player의 초기위치 설정
		initBackgroundPlayerService();
	}
	
	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}
	private void initSetting() {
		//player의 초기위치 설정 
		x = 80;
		y = 535;
		
		left = false;
		right = false;
		up = false;
		down = false;
		
		leftWallCrash = false;
		rightWallCrash = false;
		
		
		playerWay = PlayerWay.RIGHT;
		//Player자체가 JLable이라서 그냥 PlayerR을 넣으면 됨.
		setIcon(playerR); //"this"생략 가능. this.setIcon(PlayerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start();
	}
	
	
	@Override
	public void attack() {
		new Thread(()->{ //물방울 스레드를 생성하는 위치를 Bubble->Player로 바꿈
			Bubble bubble = new Bubble(mContext);
			mContext.add(bubble);
			if(playerWay == PlayerWay.LEFT) {
				bubble.left();
			}
			else {
				bubble.right();
			}	
		}).start();
		
	}
	
	
	@Override
	public void left() {
		//System.out.println("left");
		playerWay = PlayerWay.LEFT;
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
		//System.out.println("right");
		playerWay = PlayerWay.RIGHT;
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
		//System.out.println("up");
		up = true;
		new Thread(() -> {
			for(int i=0;i<130/JUMPSPEED;i++) { //for문은 상승하는 과정이다.
				y = y-JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(5);
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
		//System.out.println("down");
		down = true;
		new Thread(()->{
			while(down) { //while동안 하강한다.
				y = y+JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			down = false; //더이상 하강하지 않으므로 false로 표시를 해준다.
			
		}).start();
	}
}
