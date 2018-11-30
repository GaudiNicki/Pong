package de.gaudinicki.game;

import java.awt.event.KeyEvent;

public class Keyboard {
	
	private Keyboard() { 
	
	}
	
	public static void update() {
		
	}
	
	public static void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			Player.setMoveUp(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {

			Player.setMoveDown(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			Ball.setMoveable(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			Game.reset = true;
			Game.end = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			Game.reset = false;
			Game.end = true;
		}
	}
	
	public static void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			Player.setMoveUp(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			Player.setMoveDown(false);
		}
	}	
}
