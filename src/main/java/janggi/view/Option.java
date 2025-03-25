package janggi.view;

import janggi.common.ErrorMessage;
import java.util.Arrays;

public enum Option {
    SELECT_PIECE("1"),
    CHECK_SCORE("2"),
    CLOSE("3"),
    ;

    private final String input;

    Option(String input) {
        this.input = input;
    }

    public static Option find(String input) {
        return Arrays.stream(values())
                .filter(option -> option.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_OPTION_INPUT.getMessage()));
    }
}
