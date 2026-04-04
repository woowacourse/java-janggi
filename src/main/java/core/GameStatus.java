package core;

public enum GameStatus {
    PLAYING,
    CHO_WIN_BY_GUNG,
    HAN_WIN_BY_GUNG,
    SCORE_DECIDED;

    public boolean isOver() {
        return this != PLAYING;
    }

    public boolean isChoWinByGung() {
        return this == CHO_WIN_BY_GUNG;
    }

    public boolean isHanWinByGung() {
        return this == HAN_WIN_BY_GUNG;
    }
}
