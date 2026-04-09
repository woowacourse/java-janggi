package core;

public enum GameStatus {
    PLAYING,
    CHO_WIN_BY_GUNG,
    HAN_WIN_BY_GUNG,
    CHO_WIN_BY_SCORE,
    HAN_WIN_BY_SCORE,
    ;

    public boolean isPlaying() {
        return this == PLAYING;
    }
}
