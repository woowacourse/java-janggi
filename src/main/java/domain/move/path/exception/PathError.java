package domain.move.path.exception;

public enum PathError {

    CANNOT_MOVE_DESTINATION_IS_SAME_TEAM("같은 팀의 위치로 이동할 수 없습니다."),
    CANNOT_MOVE_PATH_HAS_OBSTACLE("이동 경로에 다른 기물이 있어 통과할 수 없습니다."),
    CANNON_MUST_JUMP_ONE_PIECE("포는 반드시 한가지 기물만 넘어야 합니다."),
    CANNON_CANNOT_JUMP_CANNON("포는 포를 넘어갈 수 없습니다"),
    CANNON_CANNOT_ATTACK_CANNON("포는 포를 공격할 수 없습니다."),

    GENERAL_CANNOT_GO_OUT_PALACE("장군은 궁성을 벗어날 수 없습니다."),
    GUARD_CANNOT_GO_OUT_PALACE("사는 궁성을 벗어날 수 없습니다."),
    ;

    private final String message;

    PathError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
