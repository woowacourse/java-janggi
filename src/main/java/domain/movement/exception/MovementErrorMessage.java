package domain.movement.exception;

public enum MovementErrorMessage {
    NULL_DIRECTIONS("방향 조합은 null일 수 없습니다."),
    EMPTY_DIRECTIONS("방향 조합은 비어 있을 수 없습니다."),
    NULL_DIRECTION_IN_SEQUENCE("방향 조합에는 null 방향이 포함될 수 없습니다."),
    EMPTY_DIRECTION_SEQUENCE_RESULT("이동 경로가 비어 있어 마지막 위치를 반환할 수 없습니다.");

    private final String message;

    MovementErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
