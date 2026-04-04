package view.message;

import controller.GameCommand;

import java.util.Arrays;

public enum GameCommandFormatter {

    COMMAND_1(1, GameCommand.MOVE),
    COMMAND_2(2, GameCommand.PASS),
    COMMAND_3(3, GameCommand.SURRENDER);

    private final int index;
    private final GameCommand gameCommand;

    GameCommandFormatter(int index, GameCommand gameCommand) {
        this.index = index;
        this.gameCommand = gameCommand;
    }

    public static GameCommand from(int index) {
        return Arrays.stream(values())
                .filter(v -> v.index == index)
                .map(v -> v.gameCommand)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령 번호입니다: " + index));
    }
}
