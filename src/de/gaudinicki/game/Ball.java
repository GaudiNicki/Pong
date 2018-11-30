package de.gaudinicki.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ball {

	private static final int WIDTH = 15;
	private static final int HEIGHT = 15;
	
	private static int x = Game.WIDTH / 2 - WIDTH / 2;
	private static int y = Game.HEIGHT / 2 - HEIGHT / 2;
	
	private static boolean moveable = false;
	
	private static int speed = 5;
	private static char directionX = Ball.randomDirection();
	private static char directionY = Ball.randomDirection();
	private static double percentX = 1;
	private static double percentY = 1;
	
	static BufferedImage ball = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	
	private static AudioHandler audio;
	
	public Ball() {}
	
	public static void update() {
		
	}
	
	public static BufferedImage render() {
		Graphics2D g2d = (Graphics2D) ball.getGraphics();
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.dispose();
		
		return ball;
	}
	
	public static void moveBall(char directionX, char directionY, double percentX, double percentY) {
		if(directionX == '-') {
			if(directionY == '-') { 
				//nach links unten
				Ball.setX((int)(Ball.getX() - speed * percentX));
				Ball.setY((int)(Ball.getY() + speed * percentY));
			}
			else { 
				//nach links oben
				Ball.setX((int)(Ball.getX() - speed * percentX));
				Ball.setY((int)(Ball.getY() - speed * percentY));
			}
		}
		else {
			if(directionY == '-') {  
				//nach rechts unten
				Ball.setX((int)(Ball.getX() + speed * percentX));
				Ball.setY((int)(Ball.getY() + speed * percentY));
			}
			else { 
				//nach rechts oben
				Ball.setX((int)(Ball.getX() + speed * percentX));
				Ball.setY((int)(Ball.getY() - speed * percentY));
			}
		}
	}
	
	public static void hitWall() {
		
		if(Ball.collisionNorth()) {
			Ball.setDirectionY('-');
			Ball.moveBall(Ball.directionX, Ball.directionY, Ball.percentX, Ball.percentY);
		}
		
		if(Ball.collisionSouth()) {
			Ball.setDirectionY('+');
			Ball.moveBall(Ball.directionX, Ball.directionY, Ball.percentX, Ball.percentY);
		}
		
		if(Ball.collisionWest()) {
			Ball.setDirectionX('+');
			Ball.moveBall(Ball.directionX, Ball.directionY, Ball.percentX, Ball.percentY);
		}
		
		if(Ball.collisionEast()) {
			Ball.setDirectionX('-');
			Ball.moveBall(Ball.directionX, Ball.directionY, Ball.percentX, Ball.percentY);
		}
	}
	
	public static void hitPlayerPaddle() {
		
		Ball.audio = AudioHandler.getInstance();
		Ball.audio.load("HitPaddleSound.mp3", "HP");
		Ball.audio.adjustVolume("HP", 0);
		Ball.audio.play("HP", 0);
		
		
		Ball.setDirectionX('-');
		
		int paddleMiddle = Player.getY() + (Player.getHEIGHT() / 2);
		int ballMiddle = Ball.getY() + (Ball.getHEIGHT() / 2);
		
		int diff = paddleMiddle - ballMiddle;
		
		if(diff >= -45 && diff < -35) {
			Ball.setPercentY(2);
		}
		
		else if(diff >= -35 && diff < -25) {
			Ball.setPercentY(1.66);
		}
		else if(diff >= -25 && diff < -10) {
			Ball.setPercentY(1.33);
		}
		else if(diff >= -10 && diff < 0) {
			Ball.setPercentY(1);
		}
		else if(diff == 0) {
			Ball.setPercentY(0.5);
		}
		else if (diff > 0 && diff <= 10) {
			Ball.setPercentY(1);
		}
		else if(diff > 10 && diff <= 25) {
			Ball.setPercentY(1.33);
		}
		
		else if(diff > 25 && diff <= 35) {
			Ball.setPercentY(1.66);
		}
		
		else if(diff > 35 && diff <= 45) {
			Ball.setPercentY(2);
		}
		
		else {
			Ball.setPercentY(0.5);
		}
		
		Ball.moveBall(Ball.directionX, Ball.directionY, Ball.percentX, Ball.percentY);
	}
	
	public static void hitOpponentPaddle() {
		
		Ball.audio = AudioHandler.getInstance();
		Ball.audio.load("HitPaddleSound.mp3", "HP");
		Ball.audio.adjustVolume("HP", 0);
		Ball.audio.play("HP", 0);
		
		Ball.setDirectionX('+');
		
		int paddleMiddle = Opponent.getY() + (Opponent.getHEIGHT() / 2);
		int ballMiddle = Ball.getY() + (Ball.getHEIGHT() / 2);
		
		int diff = paddleMiddle - ballMiddle;
		
		if(diff >= -45 && diff < -35) {
			Ball.setPercentY(2);
		}
		
		else if(diff >= -35 && diff < -25) {
			Ball.setPercentY(1.66);
		}
		else if(diff >= -25 && diff < -10) {
			Ball.setPercentY(1.33);
		}
		else if(diff >= -10 && diff < 0) {
			Ball.setPercentY(1);
		}
		else if(diff == 0) {
			Ball.setPercentY(0.5);
		}
		else if (diff > 0 && diff <= 10) {
			Ball.setPercentY(1);
		}
		else if(diff > 10 && diff <= 25) {
			Ball.setPercentY(1.33);
		}
		
		else if(diff > 25 && diff <= 35) {
			Ball.setPercentY(1.66);
		}
		
		else if(diff > 35 && diff <= 45) {
			Ball.setPercentY(2);
		}
		
		else {
			Ball.setPercentY(0.5);
		}
		
		Ball.moveBall(Ball.directionX, Ball.directionY, Ball.percentX, Ball.percentY);
	}
	
	public static boolean checkOutOfBounds() {
		if(Ball.collisionNorth() || Ball.collisionSouth() || Ball.collisionEast() || Ball.collisionWest())  {
			return true;
		}
		return false;
	}
	
	public static boolean collisionNorth() {
		if(Ball.getY() <= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean collisionSouth() {
		if(Ball.getY() >= Game.HEIGHT - Ball.getHEIGHT()) {
			return true;
		}
		return false;
	}
	
	public static boolean collisionEast() {
		if(Ball.getX() >= Game.WIDTH - Ball.getWIDTH()) {
			return true;
		}
		return false;
	}
	
	public static boolean collisionWest() {
		if(Ball.getX() <= 0) {
			return true;
		}
		return false;
	}
	
	public static void reset() {
		Ball.moveable = false;
		Ball.setX(Game.WIDTH / 2 - WIDTH / 2);
		Ball.setY(Game.HEIGHT / 2 - HEIGHT / 2);
		Ball.setDirectionX(Ball.randomDirection());
		Ball.setDirectionY(Ball.randomDirection());
		Ball.setPercentY(1);
		Ball.setPercentX(1);
	}
	
	public static char randomDirection() {
		Random rand = new Random();
		
		if(rand.nextInt(2) == 0) {
			return '-';
		}
		else {
			return '+';
		}
	}
	
	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		Ball.x = x;
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		Ball.y = y;
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}
	
	public static boolean getMoveable() {
		return Ball.moveable;
	}
	
	public static void setMoveable(boolean moveable) {
		Ball.moveable = moveable;
	}

	public static char getDirectionX() {
		return directionX;
	}

	public static void setDirectionX(char directionX) {
		Ball.directionX = directionX;
	}

	public static char getDirectionY() {
		return directionY;
	}

	public static void setDirectionY(char directionY) {
		Ball.directionY = directionY;
	}

	public static double getPercentX() {
		return percentX;
	}

	public static void setPercentX(double percentX) {
		Ball.percentX = percentX;
	}

	public static double getPercentY() {
		return percentY;
	}

	public static void setPercentY(double percentY) {
		Ball.percentY = percentY;
	}
}
