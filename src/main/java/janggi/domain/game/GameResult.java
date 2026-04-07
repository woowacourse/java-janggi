package janggi.domain.game;

import janggi.domain.side.Side;


public class GameResult {

    private static final GameResult NONE = new GameResult(false, Side.NONE);

    private final boolean gameOver;
    private final Side winner;

    private GameResult(boolean gameOver, Side winner) {
        this.gameOver = gameOver;
        this.winner = winner;
    }

    public static GameResult progress() {
        return NONE;
    }

    public static GameResult win(Side winner) {
        return new GameResult(true, winner);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Side getWinner() {
        return winner;
    }
}
