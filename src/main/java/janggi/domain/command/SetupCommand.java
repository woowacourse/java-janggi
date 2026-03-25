package janggi.domain.command;

import java.util.Arrays;

public enum SetupCommand {

    INNER_ELEPHANT(1),
    OUTER_ELEPHANT(2),
    LEFT_ELEPHANT(3),
    RIGHT_ELEPHANT(4);

    private static final int MINUMUM_NUMBER = 1;
    private static final int MAXIMUM_NUMBER = 4;

    private final int number;

    SetupCommand(final int number) {
        this.number = number;
    }

    public static SetupCommand pick(final int number) {
        return Arrays.stream(values())
            .filter(setupCommand -> setupCommand.number == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "명령 번호는 %d에서 %d까지의 정수 값이어야 합니다.", MINUMUM_NUMBER, MAXIMUM_NUMBER)));
    }
}
