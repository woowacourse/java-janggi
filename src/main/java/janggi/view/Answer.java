package janggi.view;

import java.util.Arrays;

public enum Answer {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4");

    private final String command;

    Answer(final String command) {
        this.command = command;
    }

    public static Answer from(final String number) {
        return Arrays.stream(Answer.values())
                .filter(command -> command.command.equals(number))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("예외메시지 나중에"));
    }
}
