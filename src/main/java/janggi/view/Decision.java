package janggi.view;

import java.util.Arrays;

public enum Decision {
    YES("y", true),
    NO("n", false);

    private final String input;
    private final boolean value;

    Decision(String input, boolean value) {
        this.input = input;
        this.value = value;
    }

    public static Decision from(String input) {
        return Arrays.stream(values())
                .filter(decision -> decision.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 입력입니다. (입력값: " + input + ", 기대값: y 또는 n)"));
    }

    public boolean isTrue() {
        return value;
    }
}
