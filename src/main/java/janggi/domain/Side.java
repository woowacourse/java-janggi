package janggi.domain;

import java.util.Arrays;

public enum Side {

    HAN,
    CHO;

    public static Side getSide(String value) {
        return Arrays.stream(Side.values())
                .filter(side -> side.toString().equals(value))
                .findAny()
                .orElseThrow();
    }

    public Side reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
