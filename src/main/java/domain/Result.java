package domain;

public enum Result {
    WIN("승리"),
    LOSE("패배"),
    DRAW("무승부");

    private final String message;

    Result(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
