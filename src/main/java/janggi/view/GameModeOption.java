package janggi.view;

import janggi.common.ErrorMessage;
import java.util.Arrays;

public enum GameModeOption {
    LOAD("1"),
    NEW("2"),
    ;

    private final String input;

    GameModeOption(String input) {
        this.input = input;
    }

    public static GameModeOption find(String input) {
        return Arrays.stream(values())
                .filter(gameModeOption -> gameModeOption.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_GAME_MODE_OPTION.getMessage()));
    }
}
