package controller;

public enum GameStartOption {
    NEW_GAME(1),
    LOAD_GAME(2);

    private static final String INVALID_OPTION = "올바르지 않은 게임 시작 옵션 번호입니다.";

    private final int number;

    GameStartOption(final int number) {
        this.number = number;
    }

    public static GameStartOption of(final int number) {
        if (number == NEW_GAME.number) {
            return NEW_GAME;
        }

        if (number == LOAD_GAME.number) {
            return LOAD_GAME;
        }

        throw new IllegalArgumentException(INVALID_OPTION);
    }
}
