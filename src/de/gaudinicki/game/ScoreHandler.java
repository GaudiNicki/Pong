package de.gaudinicki.game;

public class ScoreHandler {

	private static int scorePlayer = 0;
	private static int scoreOpponent = 0;
	
	private static char winner = '0';
	
	public static void incrementPlayerScore() {
		ScoreHandler.scorePlayer++;
	}
	
	public static void incrementOpponentScore() {
		ScoreHandler.scoreOpponent++;
	}
	
	public static void resetScores() {
		ScoreHandler.scorePlayer = 0;
		ScoreHandler.scoreOpponent = 0;
		ScoreHandler.setWinner('0');
	}
	
	public static int getScorePlayer() {
		return ScoreHandler.scorePlayer;
	}
	
	public static int getScoreOpponent() {
		return ScoreHandler.scoreOpponent;
	}
	
	public static char getWinner() {
		return ScoreHandler.winner;
	}
	
	public static void setWinner(char winner) {
		ScoreHandler.winner = winner;
	}
}
