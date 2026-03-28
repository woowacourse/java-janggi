package view;

import common.ErrorMessage;

public enum ActionType {
    MOVE, PASS;

    public static ActionType toValue(int value) {
        if (value == 1) {
            return MOVE;
        }
        if (value == 2) {
            return PASS;
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_ACTION_INPUT.getMessage());
    }
}
