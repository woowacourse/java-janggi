package view;

public enum ActionType {
    MOVE, PASS;

    public static ActionType toValue(int value) {
        if (value == 1) {
            return MOVE;
        }
        if (value == 2) {
            return PASS;
        }
        throw new IllegalArgumentException("잘못된 입력입니다.");
    }
}
