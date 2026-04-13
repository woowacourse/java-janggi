package janggi.controller;

import java.util.Arrays;
import java.util.List;

public enum TurnCommand {
    PLAY(1),
    SKIP(2),
    RESIGN(3);

    private final int option;

    TurnCommand(int option) {
        this.option = option;
    }

    public static TurnCommand fromOption(String option) {
        int number = validate(option);

        return Arrays.stream(values())
                .filter(command -> command.option == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("1 2 3 중 하나가 아닙니다: " + option));
    }

    private static int validate(String option) {
        int number;

        try {
            number = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(option + "는 정수가 아닙니다.");
        }

        if (!List.of(1, 2, 3).contains(number)){
            throw new IllegalArgumentException(option + "이/가 아닌 1 2 3 사이의 숫자가 입력되어야합니다.");
        }

        return number;
    }
}
