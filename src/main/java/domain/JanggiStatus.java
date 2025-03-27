package domain;

import java.util.Arrays;

public enum JanggiStatus {
    PROCESS,
    FINISH;

    public static JanggiStatus from(final boolean isFinish) {
        if (isFinish) {
            return FINISH;
        }

        return PROCESS;
    }

    public static JanggiStatus from(final String status) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(status.toUpperCase()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
