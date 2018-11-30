package de.gaudinicki.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Opponent {

	private static final int WIDTH = 15;
	private static final int HEIGHT = 60;
	
	private static int x = (Game.WIDTH - 780) - WIDTH / 2;
	private static int y = (Game.HEIGHT / 2) - HEIGHT / 2;
	
	private static int velocity = 5;
	
	static BufferedImage opponent = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	
	public Opponent() {}
	
	public static void update() {
		
	}
	
	public static BufferedImage render() {
		Graphics2D g2d = (Graphics2D) opponent.getGraphics();
		g2d.setColor(Color.RED);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.dispose();
		
		return opponent;
	}
	
	public static boolean checkBallCollision() {
		if((Opponent.getX() == Ball.getX() - Ball.getWIDTH()) && (Ball.getY() >= (Opponent.getY() - Ball.getHEIGHT()) && Ball.getY() <= (Opponent.getY() + Player.getHEIGHT() + Ball.getHEIGHT()))){
			return true;
		}
		return false;
	}
	
	public static void followBall() {
		// Berechnet die Mitte des Paddels
		int paddleMiddle = Opponent.getY() + (Opponent.getHEIGHT() / 2);
		int ballMiddle = Ball.getY() + (Ball.getHEIGHT() / 2);
		
		/* Wenn sich Ball von Paddel wegbewegt, werden die Paddel in die Mitte zurückbewegt */
		if (Ball.getDirectionX() == '+') 
		{
			if(!(paddleMiddle == Game.HEIGHT / 2)) {
				if(paddleMiddle < Game.HEIGHT / 2) {
					Opponent.setY(Opponent.getY() + Opponent.getVelocity());
				}
				else if(paddleMiddle > Game.HEIGHT / 2) {
					Opponent.setY(Opponent.getY() - Opponent.getVelocity());
				}
			}
		}
		
		//Ansonsten
		else if(Ball.getDirectionX() == '-') {
			if(paddleMiddle < ballMiddle) {
				Opponent.setY(Opponent.getY() + Opponent.getVelocity());
			}
			else if(paddleMiddle > ballMiddle) {
				Opponent.setY(Opponent.getY() - Opponent.getVelocity());
			}
		}
	}

	public static void reset() {
		Opponent.setX((Game.WIDTH - 780) - WIDTH / 2);
		Opponent.setY((Game.HEIGHT / 2) - HEIGHT / 2); 
	}
	
	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		Opponent.x = x;
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		Opponent.y = y;
	}
	
	public static int getVelocity() {
		return Opponent.velocity;
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}
}
