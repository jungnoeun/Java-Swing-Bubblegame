package bubble.test.ex13;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//메인스레드는 키보드 이벤트 처리하기 바쁨 -> 백그라운드에서 계속 관찰하고 있으려면 이클래스도 하나의 스레드가 되어야 함.
//백그라운드에서 계속 관찰
public class BackgroundPlayerService implements Runnable{

	private BufferedImage image;
	private Player player;
	
	//이미지를 받아옴
	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Override
	public void run() {
		while(true) {
			//색상확인
			Color leftColor = new Color(image.getRGB(player.getX()-10, player.getY()+25));
			Color rightColor = new Color(image.getRGB(player.getX()+50+15, player.getY()+25));
			//bottomColor가 -2가 나온다는 것은 바닥이 색깔없이 흰색이다.
			int bottomColor = image.getRGB(player.getX()+10, player.getY()+50) //왼쪽 바닥 감지 
					+ image.getRGB(player.getX()+50-10, player.getY()+50); //오른쪽 바닥 감지
			
			//바닥 충돌 확인
			if(bottomColor != -2) { // 바닥이 흰색이면 -1+-1이 나온다.
				//System.out.println("바텀 컬러 : "+ bottomColor);
				//System.out.println("바닥에 충돌함");
				player.setDown(false);
			}else { //bottomColor가 -2일때 실행됨 -> 내 바닥색깔이 하얀색이라는 것
				if(!player.isUp() && !player.isDown()) { //up일때 down실행되면 점프하자마자 down되어서 제대로된 점프를 못하고, 하강(down)중에 계속 하강(down)하면 안됨
					//System.out.println("다운 실행됨");
					player.down();
				}
			}
			
			//좌우 외벽 충돌 확인
			if(leftColor.getRed()==255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				//System.out.println("왼쪽 벽에 충돌함");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			}else if(rightColor.getRed()==255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0){
				//System.out.println("오른쪽 벽에 충돌함");
				player.setRightWallCrash(true);
				player.setRight(false);
			}else { //벽에 충돌후 움직일 수 있게 해줌.
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			}

}
