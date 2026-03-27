package janggi.common;

public enum ErrorMessage {

    INVALID_HORSE_ELEPHANT_POSITION_INPUT_FORMAT("상차림 법을 숫자로 입력해주세요."),
    INVALID_HORSE_ELEPHANT_POSITION_INPUT_RANGE("1, 2, 3, 4 중 하나의 숫자를 입력해주세요."),
    INVALID_POSITION_FORMAT("위치를 콤마로 구분된 두 개의 숫자로 올바르게 입력해주세요."),
    NO_AVAILABLE_MOVES("선택된 기물이 이동할 수 있는 위치가 없습니다."),
    PIECE_NOT_FOUND("해당 위치에 기물이 존재하지 않습니다."),
    INVALID_PIECE_OWNER("본인 팀의 기물만 옮길 수 있습니다."),
    INVALID_PIECE_MOVE("해당 위치에 해당 기물을 옮길 수 없습니다.")
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
