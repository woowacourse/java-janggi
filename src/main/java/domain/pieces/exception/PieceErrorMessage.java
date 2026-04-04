package domain.pieces.exception;

public enum PieceErrorMessage {
    CHA_INVALID_MOVE("차의 행마법으로는 해당 위치로 이동할 수 없습니다."),
    PO_INVALID_MOVE("포의 행마법으로는 해당 위치로 이동할 수 없습니다."),
    PO_ONE_SPACE_MOVE("포는 한 칸만 이동할 수 없습니다."),
    GUNG_INVALID_MOVE("궁의 행마법으로는 해당 위치로 이동할 수 없습니다."),
    SA_INVALID_MOVE("사의 행마법으로는 해당 위치로 이동할 수 없습니다."),
    JOL_BYEONG_INVALID_MOVE("졸병의 행마법으로는 해당 위치로 이동할 수 없습니다."),
    MA_INVALID_MOVE("마의 행마법으로는 해당 위치로 이동할 수 없습니다."),
    SANG_INVALID_MOVE("상의 행마법으로는 해당 위치로 이동할 수 없습니다."),
    INVALID_PATH("출발지와 도착지의 좌표가 유효하지 않습니다."),
    NO_PIECE("해당 위치에 기물이 없습니다.");

    private final String message;

    PieceErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
