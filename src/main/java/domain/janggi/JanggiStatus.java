package domain.janggi;

import java.util.Arrays;

public enum JanggiStatus {
    PROCESS("진행중"),
    FINISH("종료");

    private final String title;

    JanggiStatus(final String title) {
        this.title = title;
    }

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

    public String getTitle() {
        return title;
    }
}
