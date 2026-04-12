package view.message;

import controller.CommandType;

import java.util.Arrays;

public enum GameCommandFormatter {

    COMMAND_1(1, CommandType.MOVE, "기물 이동하기"),
    COMMAND_2(2, CommandType.PASS, "한수 쉼"),
    COMMAND_3(3, CommandType.SURRENDER, "기권");

    private final int index;
    private final CommandType commandType;
    private final String message;

    GameCommandFormatter(int index, CommandType commandType, String message) {
        this.index = index;
        this.commandType = commandType;
        this.message = message;
    }

    public static String format(int index) {
        return findBy(index).message;
    }

    public static CommandType from(int index) {
        return findBy(index).commandType;
    }


    public static GameCommandFormatter findBy(int index) {
        return Arrays.stream(values())
                .filter(v -> v.index == index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령 번호입니다: " + index));
    }
}
