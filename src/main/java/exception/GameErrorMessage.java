package exception;

public enum GameErrorMessage {
    DEFAULT_GAME_ERROR("게임 도중 문제가 발생하였습니다. 관리자에게 문의하세요."),
    /**
     * 잘못된 입력에 대한 예외 발생 메시지
     */
    INVALID_GAME_INPUT("잘못된 입력입니다. 다시 입력해주세요."),
    INVALID_HORSE_ELEPHANT_FORMATION("상마상마 상차림 입력이 올바르지 않습니다. 다음 4가지 중 하나를 입력해주세요: 상마상마, 마상마상, 상마마상, 마상상마"),
    PIECE_NOT_FOUND("해당 위치에는 기물이 없습니다. 다시 입력해주세요."),
    INVALID_TEAM_TURN("현재는 %s의 차례입니다. 같은 팀 기물을 선택해주세요."),
    INVALID_PIECE_TYPE("해당 위치에 %s가 없습니다. 다시 입력해주세요."),
    INVALID_POSITION_RANGE("기물의 도착 지점이 판 범위를 넘어섰습니다. 다시 입력해주세요."),
    INVALID_MOVE("해당 위치로 옮길 수 없습니다. 다시 입력해주세요."),

    INVALID_POSITION_FORMAT("올바르지 않은 좌표 형식입니다. \"1,1\"과 같이 입력해주세요.");

    private final String message;

    GameErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
