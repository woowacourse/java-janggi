package janggi.exception;

public enum ErrorCode {

    //path
    ROUTE_RESOLVE_ERROR("기물이 도착지에 도달할 수 없습니다."),

    //collision
    COLLISION_DETECT_ERROR("경로에 기물이 존재합니다"),
    DESTINATION_OCCUPIED_SAME_TEAM_ERROR("도착지에 같은 팀이 존재합니다."),

    //po
    PO_REQUIRED_SCREEN_COUNT_ERROR("포는 반드시 하나의 기물을 넘어야 합니다."),
    PO_EXISTENCE_IN_PATH_ERROR("이동 경로 또는 도착지에 포가 존재할 수 없습니다."),

    //jolbyeong
    JOLBYEONG_MOVE_BACK_ERROR("졸병은 뒤로 이동할 수 없습니다.");
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
