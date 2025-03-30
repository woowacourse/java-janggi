package janggi.controller;

public class GameMode {

    private GameStatus gameStatus;

    public GameMode() {
        this.gameStatus = GameStatus.PREPARE;
    }

    public boolean isPlaying() {
        return gameStatus == GameStatus.PLAY;
    }

    public void startPlaying() {
        gameStatus = GameStatus.PLAY;
    }

    public void stopPlaying() {
        gameStatus = GameStatus.END;
    }
}
