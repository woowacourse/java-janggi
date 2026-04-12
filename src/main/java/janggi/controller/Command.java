package janggi.controller;

import java.util.Arrays;

public enum Command {
    LOAD("load"),
    NEW("new"),
    DELETE("delete"),
    EXIT("exit");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.value.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 명령어를 입력해주세요."));
    }
}
