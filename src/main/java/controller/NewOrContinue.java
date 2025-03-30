package controller;

import java.util.Arrays;

public enum NewOrContinue {

    NEW("new"),
    CONTINUE("continue");

    private final String value;

    NewOrContinue(String value) {
        this.value = value;
    }

    public static NewOrContinue find(String input) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령입니다."));
    }
}
