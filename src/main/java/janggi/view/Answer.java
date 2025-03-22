package janggi.view;

import janggi.setting.AssignType;
import java.util.Arrays;

public enum Answer {
    ONE("1", AssignType.LEFT_SANG),
    TWO("2", AssignType.RIGHT_SANG),
    THREE("3", AssignType.IN_SANG),
    FOUR("4", AssignType.OUT_SANG);

    private final String command;
    private final AssignType assignType;

    Answer(final String command, final AssignType assignType) {
        this.command = command;
        this.assignType = assignType;
    }

    public static AssignType from(final String number) {
        return Arrays.stream(Answer.values())
                .filter(command -> command.command.equals(number))
                .findFirst()
                .map(value -> value.assignType)
                .orElseThrow(() -> new IllegalArgumentException("예외메시지 나중에"));
    }
}
