package exception;

public enum ErrorMessage {
    OUT_OF_RANGE_CHOICE("목록 범위 안의 번호를 입력하세요."),
    INVALID_NUMERIC_INPUT("숫자를 입력하세요."),
    INVALID_FORMATION_CHOICE("존재하지 않는 상차림 번호입니다."),
    MOVE_COORDINATE_YX_FORMAT("좌표는 y x 형식이어야 합니다."),
    INVALID_MOVE_FORMAT("올바른 이동 좌표 형식이 아닙니다."),
    PIECE_DB_ID_MISSING("이동할 기물의 정보가 없습니다."),
    INVALID_POINT("잘못된 좌표 입력입니다."),
    INVALID_TURN("현재 턴에 해당하는 팀의 기물만 움직일 수 있습니다."),
    INVALID_MOVE_DIRECTION("해당 기물이 이동할 수 없는 위치/방향입니다."),
    OBSTACLE_IN_PATH("이동 경로에 다른 기물이 있어 통과할 수 없습니다."),
    CANNON_CANNOT_JUMP_CANNON("포는 포를 넘어갈 수 없습니다."),
    CANNON_MUST_JUMP_PIECE("포는 반드시 기물 하나를 넘어야 합니다."),
    CANNON_CANNOT_TARGET_CANNON("포는 포를 공격할 수 없습니다."),
    SAME_TEAM_TARGET("같은 팀의 위치로 이동할 수 없습니다.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
