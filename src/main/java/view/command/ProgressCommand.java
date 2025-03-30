package view.command;

import java.util.Arrays;

public enum ProgressCommand {

    MOVE("move"),
    STATUS("status"),
    SAVE("save"),
    EXIT("exit"),
    ;

    private final String input;

    ProgressCommand(final String input) {
        this.input = input;
    }

    public static ProgressCommand from(final String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(input + ": [ERROR] 올바른 진행 커맨드를 입력해주세요."));
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isStatus() {
        return this == STATUS;
    }

    public boolean isSave() {
        return this == SAVE;
    }

    public boolean isExit() {
        return this == EXIT;
    }
}
