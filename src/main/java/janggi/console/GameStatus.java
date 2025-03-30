package janggi.console;

public enum GameStatus {

    ENDED(true),
    PLAYING(false),
    CHO_WIN(true),
    HAN_WIN(true);

    private final boolean isGameFinished;

    GameStatus(final boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }
}
