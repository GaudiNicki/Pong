package de.gaudinicki.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player {

	private static final int WIDTH = 15;
	private static final int HEIGHT = 60;
	
	private static int x = (Game.WIDTH - 20) - WIDTH / 2;
	private static int y = (Game.HEIGHT / 2) - (HEIGHT / 2);
	
	private static int velocity = 3;
	
	static BufferedImage player = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	
	private static boolean moveUp;
	private static boolean moveDown;
	
	public Player() {}
	
	public static void update() {
		
	}
	
	public static BufferedImage render() {
		Graphics2D g2d = (Graphics2D) player.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.dispose();
		
		if(moveUp) {
			if(ScoreHandler.getWinner() == '0') {
				if(Player.getY() >= 0) {
					Player.setY(Player.getY() - Player.getVelocity());
				}
			}
		}
		
		if(moveDown) {
			if(ScoreHandler.getWinner() == '0') {
				if(Player.getY() + Player.getHEIGHT() <= Game.HEIGHT) {
					Player.setY(Player.getY() + Player.getVelocity());
				}
			}
		}
		
		return player;
	}
	
	public static boolean checkBallCollision() {
		if((Player.getX() == Ball.getX() + Ball.getWIDTH()) && (Ball.getY() >= (Player.getY() - Ball.getHEIGHT()) && Ball.getY() <= (Player.getY() + Player.getHEIGHT() + Ball.getHEIGHT()))){
			return true;
		}
		return false;
	}
	
	public static void reset() {
		Player.setX((Game.WIDTH - 20) - WIDTH / 2);
		Player.setY((Game.HEIGHT / 2) - (HEIGHT / 2));
	}
	
	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		Player.x = x;
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		Player.y = y;
	}
	
	public static int getVelocity() {
		return Player.velocity;
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static boolean isMoveUp() {
		return moveUp;
	}

	public static void setMoveUp(boolean moveUp) {
		Player.moveUp = moveUp;
	}

	public static boolean isMoveDown() {
		return moveDown;
	}

	public static void setMoveDown(boolean moveDown) {
		Player.moveDown = moveDown;
	}
}
