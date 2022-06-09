package bubble.test.ex11;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{ //bubble도 이미지니까 JLabel이 되어야 한다.
	
	//의존성 컴포지션
	private Player player;
	
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
	
	public Bubble(Player player) {
		this.player = player;
		initObject();
		initSetting();
		initThread();
	}
	
	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");
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
	
	private void initThread() {
		// 버블은 동시이동이 불가하므로 스레드가 하나만 필요하다. 
		new Thread(()->{
			if(player.getPlayerWay() == PlayerWay.LEFT) {
				left();
			}else {
				right();
			}
		}).start();
	}

	@Override
	public void left() {
		left = true;
		for(int i=0;i<400;i++) {
			x--;
			setLocation(x, y);
			
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
			
			//그냥 하면 물방울 스레드가 너무 빠르니까 Thread.sleep(1)을 추가한다.
			try { 
				Thread.sleep(1); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}









