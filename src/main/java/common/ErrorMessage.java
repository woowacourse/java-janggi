package common;

public enum ErrorMessage {
    INVALID_POS_INPUT("[ERROR] 잘못된 좌표입니다. 다시 입력하세요."),
    PATH_BLOCKED("[ERROR] 기물이 이동하는 경로에 다른 기물이 존재하여 이동할 수 없습니다."),
    EMPTY_POSITION("[ERROR] 선택한 위치에 이동할 수 있는 기물이 존재하지 않습니다."),
    CANNOT_JUMP_PO("[ERROR] 포는 다른 포를 뛰어넘거나 목적지로 취할 수 없습니다."),
    CANNOT_MOVE("[ERROR] 지정된 목적지로 기물을 이동할 수 없습니다."),
    OUT_OF_INPUT_RANGE("1~4 사이의 숫자만 입력해주세요."),
    NOT_MATCH_PIECE("일치하는 기물 정보가 없습니다."),
    DESTINATION_ALLY("이동할 수 없습니다. (목적지에 아군이 존재함)"),
    INVALID_ACTION_INPUT("잘못된 입력입니다."),
    ONLY_ALLY("아군만 이동할 수 있습니다.");

    private final String message;

    public String getMessage() {
        return message;
    }

    ErrorMessage(String message) {
        this.message = message;
    }
}
