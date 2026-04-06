package exception;

public enum ErrorMessage {
    EMPTY_SOURCE("선택한 위치에 기물이 없습니다."),
    OUT_OF_BOARD("장기판 범위를 벗어난 위치입니다."),
    NOT_MOVE("기물이 이동하지 않았습니다."),
    SAME_TEAM_OCCUPIED("이동하려는 위치에 내 기물이 있습니다."),
    PATH_BLOCKED("멱이 막혀있습니다. 기물을 움직일 수 없습니다."),
    INVALID_MOVE_RULE("기물의 이동 규칙에 어긋납니다."),
    CANNON_NEEDS_BRIDGE("포는 반드시 한 기물을 넘어야합니다."),
    CANNON_CANNOT_OVER_PIECES("포는 두 기물 이상 넘을 수 없습니다."),
    CANNON_CANNOT_OVER_CANNON("포는 포를 넘을 수 없습니다."),
    CANNON_CANNOT_TAKE_CANNON("포는 포를 잡을 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
