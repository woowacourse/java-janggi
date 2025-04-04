package janggi.view;

import janggi.domain.setting.AssignType;
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
                .orElseThrow(() -> new IllegalArgumentException("1,2,3,4번 희망하는 숫자를 입력하셔야 합니다."));
    }
}
