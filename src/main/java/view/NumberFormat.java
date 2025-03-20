package view;

import java.util.Arrays;
import java.util.Objects;

public enum NumberFormat {

    일(1),
    이(2),
    삼(3),
    사(4),
    오(5),
    육(6),
    칠(7),
    팔(8),
    구(9),
    십(10);

    private final int number;

    NumberFormat(final int number){
        this.number = number;
    }

    public static String findNumberName(final int value){
        return Arrays.stream(values())
                .filter(v -> v.number == value)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("버그입니다."))
                .name();
    }

    public static int findNumber(final String numberName){
        return Arrays.stream(values())
                .filter(v -> Objects.equals(v.name(), numberName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 좌표 번호입니다."))
                .number;
    }
}
