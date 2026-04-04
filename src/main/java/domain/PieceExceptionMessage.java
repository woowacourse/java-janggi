package domain;

public enum PieceExceptionMessage {
    INVALID_POSITION("해당 기물로 도달할 수 없는 위치입니다."),
    BLOCKED_BY_PIECE("다른 기물에 가로막혀 이동할 수 없습니다."),
    CANT_JUMP_OVER_PO("포는 포를 뛰어넘을 수 없습니다."),
    CANT_JUMP_OVER_THAN_TWO_PIECES("포는 두 기물 이상 뛰어넘을 수 없습니다."),
    PO_SHOULD_JUMP_ONE_PIECE("포는 하나의 기물을 뛰어넘어야 이동할 수 있습니다"),
    DESTINATION_HAS_ALLY("목적지에 아군이 존재하여 이동할 수 없습니다."),
    PO_CANT_CAPTURE_PO("포는 포를 잡을 수 없습니다."),
    CAN_NOT_GO_OUT_OF_GUNGSEONG("해당 기물은 궁성 밖으로 이동할 수 없습니다.");
    private final String message;

    PieceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
