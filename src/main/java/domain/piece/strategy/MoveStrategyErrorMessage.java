package domain.piece.strategy;

public enum MoveStrategyErrorMessage {
    NOT_EXIST_MOVABLE_PATH("목적지까지 이동 가능한 경로가 없습니다."),
    EMPTY_PIECE("해당 기물의 이동 가능 경로 확인을 할 수 없습니다");

    private final String message;

    public String getMessage() {
        return message;
    }

    MoveStrategyErrorMessage(String message) {
        this.message = message;
    }
}
