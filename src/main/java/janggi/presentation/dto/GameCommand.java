package janggi.presentation.dto;

import java.util.Arrays;

public enum GameCommand {
    NEW("y"),

    LOAD("n");

    private final String input;

    GameCommand(String input) {
        this.input = input;
    }

    public static GameCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] y 또는 n만 입력 가능합니다."));
    }

    public boolean isNewGame() {
        return this == NEW;
    }
}
