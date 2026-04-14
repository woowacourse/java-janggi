package janggi.view.dto;

import java.util.Arrays;

public enum Command {
    MOVE("y"),
    GIVE_UP("q"),
    DRAW("d");

    private final String input;

    Command(String input) {
        this.input = input;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input))
                .findFirst()
                .orElse(Command.MOVE);
    }

    public boolean isGiveUp() {
        return this == GIVE_UP;
    }

    public boolean isDraw() {
        return this == DRAW;
    }
}
