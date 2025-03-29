package janggi.domain.board;

public enum GameState {
    RUN,
    GAME_END,
    USER_END
    ;

    public boolean isRun() {
        return this == RUN;
    }
}
