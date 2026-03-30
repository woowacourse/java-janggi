package domain;

import java.util.Arrays;

public enum FormationCommand {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    ;
    
    private final String input;

    FormationCommand(String input) {
        this.input = input;
    }

    public static FormationCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input.strip()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 입력이 아닙니다."));
    }
}
