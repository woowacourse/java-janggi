package domain;

public enum GameState {
    RUNNING,
    END;

    public boolean isRunning() {
        return this == RUNNING;
    }
}
