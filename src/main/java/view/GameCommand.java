package view;

import java.util.Arrays;

public enum GameCommand {
    CREATE_NEW_GAME_COMMAND("1"),
    LOAD_GAME_COMMAND("2"),
    ;

    private final String description;

    GameCommand(String description) {
        this.description = description;
    }

    public static GameCommand convertToCommand(String description) {
        return Arrays.stream(values())
                .filter(command -> command.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 커맨드 입니다."));
    }

    public String getDescription() {
        return description;
    }
}
