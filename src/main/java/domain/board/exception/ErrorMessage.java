package domain.board.exception;

import common.exception.ExceptionInformation;

public enum ErrorMessage implements ExceptionInformation {

    FORMATION_IS_NOT_NUMERIC("포메이션 번호는 숫자로 입력해주세요."),
    FORMATION_NUMBER_RANGE_IS_INVALID("포메이션 번호는 1-4사이의 숫자입니다."),
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
