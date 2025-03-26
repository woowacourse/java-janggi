package view;

import java.util.Arrays;

public enum Command {

    MOVE("move"),
    STATUS("status"),
    END("end"),
    ;

    private final String input;

    Command(String input) {
        this.input = input;
    }

    public static Command from(String input) {
        return Arrays.stream(Command.values())
                .filter(command -> command.input.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(input + ": 존재하지 않는 명령어입니다."));
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isStatus() {
        return this == STATUS;
    }

    public boolean isEnd() {
        return this == END;
    }
}
