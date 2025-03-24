package controller;

import java.util.Arrays;

public enum Command {

    YES("y"),
    NO("n");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public static Command find(String input) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령입니다."));
    }
}
