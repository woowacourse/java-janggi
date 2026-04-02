package ui.dto;

public enum ActionType {
    MOVE, PASS;
    private static final String INVALID_ACTION_TYPE = "잘못된 입력입니다";

    public static ActionType toValue(int value) {
        if (value == 1) {
            return MOVE;
        }
        if (value == 2) {
            return PASS;
        }
        throw new IllegalArgumentException(INVALID_ACTION_TYPE);
    }
}
