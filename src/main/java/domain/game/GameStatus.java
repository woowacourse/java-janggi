package domain.game;

public enum GameStatus {
    RUNNING,
    FINISHED;

    public boolean isRunning() {
        return this == RUNNING;
    }
}
