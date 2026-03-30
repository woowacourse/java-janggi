package janggi.domain.board;

import java.util.Arrays;

public enum PieceSetup {
    LEFT_ELEPHANT("1"),
    RIGHT_ELEPHANT("2"),
    INNER_ELEPHANT("3"),
    OUTER_ELEPHANT("4");

    private final String value;

    PieceSetup(String value) {
        this.value = value;
    }

    public static PieceSetup from(String value) {
        return Arrays.stream(values())
                .filter(setup -> setup.value.equals(value))
                .findAny().orElseThrow(
                        () -> new IllegalArgumentException("[ERROR] 올바른 차림 번호를 입력해주세요.")
                );
    }
}
