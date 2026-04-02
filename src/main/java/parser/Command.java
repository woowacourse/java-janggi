package parser;

import java.util.Arrays;

public enum Command {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    ;
    
    private final String input;

    Command(String input) {
        this.input = input;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input.strip()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 입력이 아닙니다."));
    }
}
