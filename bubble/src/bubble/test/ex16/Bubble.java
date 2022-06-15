package bubble.test.ex16;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{ //bubble도 이미지니까 JLabel이 되어야 한다.
	
	//의존성 컴포지션
	private Player player;
	private Enemy enemy; //적군이 여러명이면 컬렉션으로 받으면 된다.
	private BackgroundBubbleService backgroundBubbleService;
	private BubbleFrame mContext;
	
	//위치상태
	private int x;
	private int y;
		
	//움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
		
	//적군을 맞춘 상태
	private int state; //0(물방울), 1(적군을 가둔 물방울)
	
	private ImageIcon bubble; //물방울
	private ImageIcon bubbled; //적군을 가둔 물방울
	private ImageIcon bomb; //물방울이 터진 상태
	
	public Bubble(BubbleFrame mContext) {
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		this.enemy = mContext.getEnemy();
		initObject();
		initSetting();
//		initThread();
	}
	
	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");
		
		backgroundBubbleService = new BackgroundBubbleService(this);
	}
	
	private void initSetting() {
		left = false;
		right = false;
		up = false;
		
		x = player.getX();
		y = player.getY();
		
		setIcon(bubble);
		setSize(50,50);
		
		state = 0;
		
	}
	
/*	private void initThread() {
		// 버블은 동시이동이 불가하므로 스레드가 하나만 필요하다. 
		new Thread(()->{
			if(player.getPlayerWay() == PlayerWay.LEFT) {
				left();
			}else {
				right();
			}
		}).start();
	}*/

	@Override
	public void left() {
		left = true;
		for(int i=0;i<400;i++) {
			x--;
			setLocation(x, y);
			
			if(backgroundBubbleService.leftWall()) {
				left = false;
				break;
			}
			
			//40과 60의 범위 절대값
			if((Math.abs(x - enemy.getX()) < 10) && 
				(Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)) {
				System.out.println("물방울이 적군과 충돌하였습니다.");
				if(enemy.getState() == 0) {
					attack();
					break;
				}
			}
			
			//그냥 하면 물방울 스레드가 너무 빠르니까 Thread.sleep(1)을 추가한다.
			try { 
				Thread.sleep(1); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		up();
	}

	@Override
	public void right() {
		right = true;
		for(int i=0;i<400;i++) {
			x++;
			setLocation(x, y);
			
			if(backgroundBubbleService.rightWall()) {
				right = false;
				break;
			}
			
			//아군과 적군의 거리가 10차이가 나면 적군을 물방울에 가둔다.
			if((Math.abs(x - enemy.getX()) < 10) && 
				(Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)) {
				System.out.println("물방울이 적군과 충돌하였습니다.");
				if(enemy.getState() == 0) {
					attack();
					break;
				}
			}
			
			//그냥 하면 물방울 스레드가 너무 빠르니까 Thread.sleep(1)을 추가한다.
			try { 
				Thread.sleep(1); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		up();
	}

	@Override
	public void up() {
		up = true;
		while(up) {
			y--;
			setLocation(x,y);
			
			if(backgroundBubbleService.topWall()) {
				up = false;
				break;
			}
			
			
			//그냥 하면 물방울 스레드가 너무 빠르니까 Thread.sleep(1)을 추가한다.
			try {
				if(state == 0) { // 기본 물방울
					Thread.sleep(1); // 빠르게 천장으로 올라감	
				}else { // 적을 가둔 물방울
					Thread.sleep(10); // 천천히 천장으로 올라감
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(state == 0) clearBubble(); // 천장에 버블이 도착하고 나서 3초 후에 메모리에서 소멸
	}
	
	@Override //버블이 적군을 공격
	public void attack() {
		state = 1;
		enemy.setState(1);
		setIcon(bubbled);
		mContext.remove(enemy); // 메모리에서 적군을 사라지게 한다. (가비지 컬렉션이 즉시 발생하지 않는다.)
		mContext.repaint(); // 화면 갱신
	}
	
	
	// 행위 -> clear(동사) -> bubble(목적어)
	private void clearBubble() {
		try {
			Thread.sleep(3000);
			setIcon(bomb);
			Thread.sleep(500);
			mContext.remove(this); // BubbleFrame의 bubble이 메모리에서 소멸된다.
			mContext.repaint(); // BubbleFrame의 전체를 다시 그린다. (메모리에 없는 건 그리지 않음)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}









