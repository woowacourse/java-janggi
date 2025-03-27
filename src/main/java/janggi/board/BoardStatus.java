package janggi.board;

public enum BoardStatus {

    IN_PROGRESS("진행중"),
    CHO_WIN("초나라 승리!"),
    HAN_WIN("한나라 승리!"),
    ;

    private final String message;

    BoardStatus(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
