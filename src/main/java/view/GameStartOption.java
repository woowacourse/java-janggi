package view;

public final class GameStartOption {

    private static final String NEW_GAME = "1";
    private static final String CONTINUE_GAME = "2";
    public static final String ERROR_INVALID_GAME_START_OPTION = "[ERROR] 1 또는 2만 입력할 수 있습니다.";

    private final String value;

    private GameStartOption(String value) {
        validate(value);
        this.value = value;
    }

    public static GameStartOption from(String value) {
        return new GameStartOption(value);
    }

    private void validate(String value) {
        if (!NEW_GAME.equals(value) && !CONTINUE_GAME.equals(value)) {
            throw new IllegalArgumentException(ERROR_INVALID_GAME_START_OPTION);
        }
    }

    public boolean isContinueGame() {
        return CONTINUE_GAME.equals(value);
    }
    
}
