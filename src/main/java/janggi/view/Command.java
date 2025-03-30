package janggi.view;

import janggi.exception.ErrorException;
import java.util.Arrays;

public enum Command {

    START("START", "게임 시작"),
    RESUME("RESUME", "게임 계속"),
    MOVE("MOVE", "기물 이동"),
    END("END", "게임 종료"),
    ;

    private static final String COMMAND_FORMAT = "• %-6s : %s";

    private final String code;
    private final String name;

    Command(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Command findCommandByCode(String input) {
        return Arrays.stream(Command.values())
                .filter(command -> command.isCommand(input))
                .findFirst()
                .orElseThrow(() -> new ErrorException("존재하지 않는 명령어입니다."));
    }

    private boolean isCommand(String input) {
        return this.code.compareToIgnoreCase(input) == 0;
    }

    public String toString() {
        return String.format(COMMAND_FORMAT, code, name);
    }
}
