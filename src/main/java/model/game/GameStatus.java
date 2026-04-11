package model.game;

public enum GameStatus {
    PLAYING,
    WIN_BY_CAPTURE,
    WIN_BY_SCORE,
    QUIT;

    public boolean isFinished() {
        return this != PLAYING;
    }
}
