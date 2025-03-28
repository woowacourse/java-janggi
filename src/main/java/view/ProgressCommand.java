package view;

import java.util.Arrays;

public enum ProgressCommand {

    START("start"),
    MOVE("move"),
    STATUS("status"),
    EXIT("exit"),
    ;

    private final String input;

    ProgressCommand(String input) {
        this.input = input;
    }

    public static ProgressCommand from(final String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(input + ": [ERROR] 올바른 진행 커맨드를 입력해주세요."));
    }
}
