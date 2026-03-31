package domain.piece;

public enum PieceErrorMessage {
    PATH_BLOCKED("목적지까지 이동 가능한 경로에 다른 기물이 존재하여 이동할 수 없습니다."),
    NOT_YOUR_PIECE("당신의 기물이 아닙니다"),
    ALREADY_OCCUPIED_BY_ALLY("아군의 위치로는 이동할 수 없습니다"),
    PO_CANNOT_JUMP_PO("포는 포를 넘을 수 없습니다."),
    PO_CANNOT_CAPTURE_PO("포는 포를 잡을 수 없습니다."),
    INVALID_PO_SCREEN_COUNT("포는 반드시 하나의 기물을 넘어야 이동할 수 있습니다."),
    EMPTY_PIECE("해당 기물의 이동 가능성 확인을 할 수 없습니다");

    private final String message;

    public String getMessage() {
        return message;
    }

    PieceErrorMessage(String message) {
        this.message = message;
    }
}
