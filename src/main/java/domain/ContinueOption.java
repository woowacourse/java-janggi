package domain;

import java.util.Arrays;

public enum ContinueOption {
    CONTINUE("yes"),
    RESTART("no");

    private final String value;

    ContinueOption(String value) {
        this.value = value;
    }

    public static ContinueOption from(String input) {
        return Arrays.stream(values())
                .filter(option -> option.value.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] yes 또는 no를 입력해주세요."));
    }
}
