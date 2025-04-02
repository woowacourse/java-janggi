package janggi.view.answer;

import java.util.Arrays;

public enum TurnMenuAnswer {
    ONE("1"),
    TWO("2"),
    THREE("3");

    private final String command;

    TurnMenuAnswer(String command) {
        this.command = command;
    }

    public static TurnMenuAnswer parse(final String number) {
        return Arrays.stream(TurnMenuAnswer.values())
                .filter(answer -> answer.getCommand().equals(number))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절하지 않은 입력값입니다."));
    }

    public String getCommand() {
        return command;
    }
}
