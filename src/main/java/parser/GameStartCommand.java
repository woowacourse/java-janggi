package parser;

import java.util.Arrays;

public enum GameStartCommand {
    NEW_GAME("1"),
    LOAD_GAME("2"),
    ;

    private final String input;

    GameStartCommand(String input) {
        this.input = input;
    }

    public static GameStartCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input.strip()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 시작 메뉴를 입력하세요. (1: 새로 시작, 2: 게임 불러오기)"));
    }

    public boolean isNewGame() {
        return this == NEW_GAME;
    }
}
