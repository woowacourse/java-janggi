package domain.command.exception;

public enum CommandError {

    MOVE_COMMAND_IS_NOT_NUMERIC("이동 명령어는 숫자로 입력해야합니다."),
    MOVE_COMMAND_INPUT_IS_BLANK("이동 좌표 입력값이 비어있습니다."),
    MOVE_COMMAND_FORMAT_IS_WRONG("명령어는 'y,x y,x' 형식으로 총 2개의 좌표를 입력해야합니다."),

    BOARD_SELECT_IS_NOT_NUMERIC("장기게임 선택은 숫자로 입력해야합니다."),
    INVALID_BOARD_SELECT("올바르지 않은 입력입니다. (-1: 종료 / 0: 새 게임 / 양수: 게임 선택)"),
    ;

    private final String message;

    CommandError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
