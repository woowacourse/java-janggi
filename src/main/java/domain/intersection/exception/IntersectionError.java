package domain.intersection.exception;

public enum IntersectionError {

    ORIGIN_INTERSECTION_IS_NOT_OPPONENT("상대 칸을 출발좌표로 지정할 수 없습니다."),
    ORIGIN_INTERSECTION_IS_EMPTY("빈 칸을 출발좌표로 정할 수 없습니다.");

    private final String message;

    IntersectionError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
