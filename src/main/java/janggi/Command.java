package janggi;

import java.util.Arrays;

public enum Command {

    STOP("end"),
    MOVE("move [0-9][0-8] [0-9][0-8]");

    private final String pattern;

    Command(final String pattern) {
        this.pattern = pattern;
    }

    public static Command of(final String value) {
        return Arrays.stream(Command.values())
                .filter(command -> value.matches(command.pattern))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 형식으로 입력해주세요."));
    }
}
