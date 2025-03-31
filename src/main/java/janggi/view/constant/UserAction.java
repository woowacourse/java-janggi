package janggi.view.constant;

import java.util.Arrays;

public enum UserAction {

    YES("y"),
    NO("n");

    private final String value;

    UserAction(final String value) {
        this.value = value;
    }

    public static UserAction get(final String value) {
        return Arrays.stream(UserAction.values())
                .filter(action -> action.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 값을 찾을 수 없습니다."));
    }

}
