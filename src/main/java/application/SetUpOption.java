package application;

import domain.board.SetUp;

public enum SetUpOption {
    LEFT(1, SetUp.LEFT_ELEPHANT),
    RIGHT(2, SetUp.RIGHT_ELEPHANT),
    INNER(3, SetUp.INNER_ELEPHANT),
    OUTER(4, SetUp.OUTER_ELEPHANT);

    private final int number;
    private final SetUp setUp;

    SetUpOption(int number, SetUp setUp) {
        this.number = number;
        this.setUp = setUp;
    }

    public static SetUp from(int input) {
        for (SetUpOption option : values()) {
            if (option.number == input) {
                return option.setUp;
            }
        }

        throw new IllegalArgumentException("[ERROR] 상차림 번호는 1, 2, 3, 4 중 하나여야 합니다.");
    }
}
