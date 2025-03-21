package janggi.controller;

import java.util.Arrays;

public enum Command {
    MOVE("move"),
    END("end"),
    ;

    private final String text;

    Command(final String text) {
        this.text = text;
    }

    public static Command getCommand(String target) {
        return Arrays.stream(Command.values())
                .filter(command -> target.equals(command.text))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다."));
    }
}
