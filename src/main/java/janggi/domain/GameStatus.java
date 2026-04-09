package janggi.domain;

public enum GameStatus {

    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED"),
    ;

    private final String format;

    GameStatus(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
