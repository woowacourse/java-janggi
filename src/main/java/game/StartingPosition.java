package game;

import java.util.Arrays;

public enum StartingPosition {
    RIGHT_ELEPHANT_SETUP(1),
    LEFT_ELEPHANT_SETUP(2),
    INNER_ELEPHANT_SETUP(3),
    OUTER_ELEPHANT_SETUP(4),
    ;

    private final int startOption;

    StartingPosition(int startOption) {
        this.startOption = startOption;
    }

    public static StartingPosition fromOption(int option) {
        return Arrays.stream(StartingPosition.values())
                .filter(startingPosition -> startingPosition.startOption == option)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상차림 번호입니다: " + option));
    }

}
