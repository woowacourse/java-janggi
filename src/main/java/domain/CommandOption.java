package domain;

import java.util.Arrays;

public enum CommandOption {

    MOVE("1", "기물 이동"),
    UNDO("2", "무르기");

    private final String command;
    private final String description;

    CommandOption(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static CommandOption of(String input) {
        return Arrays.stream(values())
                .filter(command -> command.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 선택지 입니다."));
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
