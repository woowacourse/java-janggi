package janggi.view;

import janggi.common.ErrorMessage;
import java.util.Arrays;

public enum MaSangPosition {
    SANG_MA_SANG_MA("1"),
    MA_SANG_MA_SANG("2"),
    MA_SANG_SANG_MA("3"),
    SANG_MA_MA_SANG("4"),
    ;

    private final String input;

    MaSangPosition(String input) {
        this.input = input;
    }

    public static MaSangPosition find(String input) {
        return Arrays.stream(MaSangPosition.values()).filter(value -> value.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MASANG_INPUT.getMessage()));
    }
}
