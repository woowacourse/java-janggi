package janggi.view;

import janggi.common.ErrorMessage;
import java.util.Arrays;

public enum PlayerOption {
    SELECT_PIECE("1"),
    CHECK_SCORE("2"),
    CLOSE("3"),
    ;

    private final String input;

    PlayerOption(String input) {
        this.input = input;
    }

    public static PlayerOption find(String input) {
        return Arrays.stream(values())
                .filter(playerOption -> playerOption.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_GAME_OPTION_INPUT.getMessage()));
    }
}
