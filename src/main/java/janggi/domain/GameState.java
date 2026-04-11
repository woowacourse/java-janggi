package janggi.domain;

public enum GameState {
    PLAYING,
    TURN_SUCCESS,
    TURN_FAILED,
    FINISHED,
    ;

    public boolean isPlaying() {
        return this != FINISHED;
    }

    public boolean isTurnSuccess() {
        return this == TURN_SUCCESS;
    }
}
