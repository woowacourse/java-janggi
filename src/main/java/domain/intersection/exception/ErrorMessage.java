package domain.intersection.exception;

import common.exception.ExceptionInformation;

public enum ErrorMessage implements ExceptionInformation {

    ORIGIN_INTERSECTION_IS_NOT_OPPONENT("상대 칸을 출발좌표로 지정할 수 없습니다."),
    ORIGIN_INTERSECTION_IS_EMPTY("빈 칸을 출발좌표로 정할 수 없습니다.")
    ;

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
