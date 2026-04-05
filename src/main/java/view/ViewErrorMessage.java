package view;

public enum ViewErrorMessage {
    FORMAT_ERROR("입력 형식을 준수해주세요"),
    OLY_NUMBER("숫자만 입력 가능합니다"),
    INVALID_ACTION_INPUT("1, 2 만 입력해주세요"),
    INVALID_SETTING_TYPE_INPUT("1 ~ 4 사이의 숫자만 입력해주세요"),
    NOT_MATCH_PIECE("일치하는 기물 정보가 없습니다"),
    NOT_DEFINED_TEAM("정의되지 않는 팀입니다."),
    ONLY_YES_OR_NO("y 혹은 n만 입력해주세요");


    private final String message;

    public String getMessage() {
        return message;
    }

    ViewErrorMessage(String message) {
        this.message = message;
    }
}
