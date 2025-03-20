package janggi.view;

import java.util.Arrays;

public enum SetupOption {

    INNER_SETUP("1"),
    OUTER_SETUP("2"),
    RIGHT_SETUP("3"),
    LEFT_SETUP("4");

    private final String option;

    SetupOption(final String option) {
        this.option = option;
    }

    public static SetupOption of(final String input) {
        return Arrays.stream(SetupOption.values())
                .filter(setupOption -> setupOption.option.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 1~4의 숫자만 입력할 수 있습니다."));
    }
}
