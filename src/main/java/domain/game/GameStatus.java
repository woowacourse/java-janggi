package domain.game;


public enum GameStatus {
    PROGRESS("PROGRESS"),
    CHO_WIN("CHO_WIN"),
    HAN_WIN("HAN_WIN");

    private final String value;

    GameStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

