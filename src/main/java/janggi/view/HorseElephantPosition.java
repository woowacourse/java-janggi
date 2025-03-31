package janggi.view;

import janggi.common.ErrorMessage;
import java.util.Arrays;

public enum HorseElephantPosition {
    ELEPHANT_HORSE_ELEPHANT_HORSE("1"),
    HORSE_ELEPHANT_HORSE_ELEPHANT("2"),
    HORSE_ELEPHANT_ELEPHANT_HORSE("3"),
    ELEPHANT_HORSE_HORSE_ELEPHANT("4"),
    ;

    private final String input;

    HorseElephantPosition(String input) {
        this.input = input;
    }

    public static HorseElephantPosition find(String input) {
        return Arrays.stream(HorseElephantPosition.values())
                .filter(value -> value.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MASANG_INPUT.getMessage()));
    }
}
