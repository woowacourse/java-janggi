package view.command;

import java.util.Arrays;

public enum RestartCommand {

    YES("y", true),
    NO("n", false),
    ;

    private final String input;
    private final boolean accepted;

    RestartCommand(String input, boolean accepted) {
        this.input = input;
        this.accepted = accepted;
    }

    public static boolean select(String input) {
        return Arrays.stream(values())
                .filter(answer -> answer.input.equals(input))
                .findFirst()
                .map(value -> value.accepted)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 예는 y, 아니오는 n을 입력하세요."));
    }
}
