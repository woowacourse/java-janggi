package view.command;

import java.util.List;

public enum CommandType {
    MOVE(List.of("move", "m")), SCORE(List.of("score", "s")), QUIT(List.of("quit", "q"));

    private final List<String> inputs;

    CommandType(List<String> inputs) {
        this.inputs = inputs;
    }

    public static CommandType from(String input) {
        String processedInput = input.strip().toLowerCase();

        for (CommandType commandType : CommandType.values()) {
            if (commandType.inputs.contains(processedInput)) {
                return commandType;
            }
        }

        throw new IllegalArgumentException("존재하지 않는 명령입니다.");
    }
}
