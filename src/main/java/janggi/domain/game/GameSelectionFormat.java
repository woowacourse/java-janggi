package janggi.domain.game;

import janggi.exception.ExceptionMessage;
import java.util.Arrays;

public enum GameSelectionFormat {

    NEW_GAME("1", "새 게임"),
    EXISTING_GAME("2", "기존 게임 불러오기"),
    ;

    private String command;
    private String description;

    GameSelectionFormat(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static GameSelectionFormat from(String input) {
        return Arrays.stream(values())
                .filter(element -> element.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.GAME_SELECTION_NOT_FOUND.getMessage()));
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
