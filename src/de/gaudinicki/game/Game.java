package de.gaudinicki.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, Runnable{

	//Frame
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	private Thread game;
	
	//Game
	private boolean running;
	private boolean run = true;
	static boolean reset = false;
	static boolean end = false;
	
	//Fonts
	public static final Font mainFont = new Font("Bebas Neue Regular", Font.PLAIN, 28);
	Font scoreFont = mainFont.deriveFont(70f);
	Font messageFont = mainFont.deriveFont(32f);
	
	//Images
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private Image backgroundImg;
	
	
	//Sound
	private AudioHandler audio;
	
	private int winningScore = 10;
	private int endCounter = 0;
	

	
	public Game() {
		
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		
		backgroundImg = Toolkit.getDefaultToolkit().getImage(Game.class.getClassLoader().getResource("Pong-Background.png")); 
				
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.drawImage(Ball.render(), WIDTH / 2 - Ball.getWIDTH() / 2, HEIGHT / 2 - Ball.getHEIGHT() / 2, Ball.getWIDTH(), Ball.getHEIGHT(), this);
		g.dispose();
		
		//Sound
		audio = AudioHandler.getInstance();
		audio.load("BackgroundMusic.mp3", "BG");
		audio.adjustVolume("BG", -20);
		audio.play("BG", Clip.LOOP_CONTINUOUSLY);
		audio.load("WinSound.mp3", "Win");
		audio.adjustVolume("Win", -10);
		audio.load("FailSound.wav", "Fail");
		audio.adjustVolume("Fail", -10);
		
	}
	
	private void render() {
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.drawImage(backgroundImg, 0, 0, WIDTH, HEIGHT, this);
		g.drawImage(Ball.render(), Ball.getX(), Ball.getY(), Ball.getWIDTH(), Ball.getHEIGHT(), this);
		g.drawImage(Player.render(), Player.getX(), Player.getY(), Player.getWIDTH(), Player.getHEIGHT(), this);
		g.drawImage(Opponent.render(), Opponent.getX(), Opponent.getY(), Opponent.getWIDTH(), Opponent.getHEIGHT(), this);
		g.setColor(Color.RED);
		g.setFont(scoreFont);
		g.drawString("" + ScoreHandler.getScoreOpponent(), 200 - DrawUtils.getMessageWidth("" + ScoreHandler.getScoreOpponent(), scoreFont, g), 100);
		g.setColor(Color.WHITE);
		g.drawString("" + ScoreHandler.getScorePlayer(), (Game.WIDTH - 200) - DrawUtils.getMessageWidth("" + ScoreHandler.getScorePlayer(), scoreFont, g), 100);
		
		if(ScoreHandler.getWinner() != '0') {
			if(ScoreHandler.getWinner() == 'p') {
				g.setColor(Color.WHITE);
				g.drawString("You win!", Game.WIDTH/2 - DrawUtils.getMessageWidth("You win!", scoreFont, g) / 2, Game.HEIGHT / 2 - DrawUtils.getMessageHeight("You win!", scoreFont, g)  / 2);
			}
			else if(ScoreHandler.getWinner() == 'o') {
				g.setColor(Color.RED);
				g.drawString("Opponent win!", Game.WIDTH/2 - DrawUtils.getMessageWidth("Opponent win!", scoreFont, g) / 2, Game.HEIGHT / 2 - DrawUtils.getMessageHeight("Opponent win!", scoreFont, g)  / 2);
			}
		}
		
		if(run == false) {
			g.setColor(Color.WHITE);
			g.setFont(messageFont);
			g.drawString("Press 'r' to restart or 'q' to quit!", Game.WIDTH / 2 - DrawUtils.getMessageWidth("Press 'r' to restart or 'q' to quit!", messageFont, g) / 2, (Game.HEIGHT - (Game.HEIGHT / 3)) - DrawUtils.getMessageHeight("Press 'r' to restart or 'q' to quit!", messageFont, g) / 2);
		}
		
		g.dispose();
		
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}
	
	private void update() {
		//render();
		checkWon();
		
		if(run) {
			if(ScoreHandler.getWinner() == '0') {
				Keyboard.update();
				
				if(Ball.getMoveable()) {
					
					Opponent.followBall();
					
					if(Ball.collisionWest()) {
						ScoreHandler.incrementPlayerScore();
						Ball.reset();
					}
					
					else if(Ball.collisionEast()) {
						ScoreHandler.incrementOpponentScore();
						Ball.reset();
					}
				
					if(!Ball.checkOutOfBounds()) {
						if(!Player.checkBallCollision() && !Opponent.checkBallCollision()) {
							Ball.moveBall(Ball.getDirectionX(), Ball.getDirectionY(), Ball.getPercentX(), Ball.getPercentY());
						}
						else {
							if(Player.checkBallCollision()) {
								Ball.hitPlayerPaddle();
							}
							if(Opponent.checkBallCollision()) {
								Ball.hitOpponentPaddle();
							}
						}
					}
					else {
						Ball.hitWall();
					}
				}
			}
			
			else {
				run = false;
			}
		}
	
		else {
			render();
			
			endCounter++;
			
			if(endCounter == 1) {
				endGame();
			}
		
			if(reset) {
				resetGame();
				startGame();
			}
			
			if(end) {
				running = false;
				System.exit(0);
			}
			
		}
	}
	
	private void endGame() {
		run = false;
		
		audio.adjustVolume("BG", -80);
		
		if(ScoreHandler.getWinner() == 'p') {
			audio.play("Win", 0);
		}
		
		else if(ScoreHandler.getWinner() == 'o') {
			audio.play("Fail", 0);
		}
		
	}

	public void resetGame() {
		ScoreHandler.resetScores();
		Ball.reset();
		Player.reset();
		Opponent.reset();
		endCounter = 0;
		reset = false;
		end = false;
	}
	
	private void startGame() {
		run = true;
		audio.adjustVolume("BG", -20);
	}
	
	private boolean checkWon() {
		if(ScoreHandler.getScoreOpponent() == winningScore) {			
			ScoreHandler.setWinner('o');
			return true;
		}
		else if(ScoreHandler.getScorePlayer() == winningScore) {
			ScoreHandler.setWinner('p');
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		
		double nsPerUpdate = 1000000000.0 / 60;
		
		//last update time in nanoseconds
		double then = System.nanoTime();
		double unprocessed = 0;
		
		while(running) {
			
			boolean shouldRender = false;
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;
			
			//update queue
			while(unprocessed >= 1) {
				update();
				unprocessed--;
				shouldRender = true;
			}
			
			//render
			if(shouldRender) {
				render();
				shouldRender = false;
			}
			else {
				try {
					Thread.sleep(1);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		game = new Thread(this, "game");
		game.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keyboard.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keyboard.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
