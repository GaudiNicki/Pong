package de.gaudinicki.game;

import javax.swing.JFrame;

public class Start {
	
	public static void main(String[] args) {
		Game game = new Game();
		
		JFrame window = new JFrame("Pong");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(game);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		game.start();
	}
}
