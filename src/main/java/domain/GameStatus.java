package domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum GameStatus {

    GREEN_PLAYER_TURN("초나라 차례"),
    RED_PLAYER_TURN("한나라 차례"),

    GREEN_TEAM_WIN("초나라 승"),
    RED_TEAM_WIN("한나라 승"),
    ;

    private static final Map<String, GameStatus> STRING_GAME_STATUS_MAP;
    private final String description;


    static {
        STRING_GAME_STATUS_MAP = Arrays.stream(GameStatus.values())
                .collect(Collectors.toMap(
                        status -> status.description,
                        status -> status)
                );
    }

    GameStatus(String description) {
        this.description = description;
    }

    public static GameStatus of(String statusDescription) {
        return GameStatus.STRING_GAME_STATUS_MAP.get(statusDescription);
    }

    public GameStatus changePlayerTurn() {
        if (this == GameStatus.GREEN_PLAYER_TURN) {
            return RED_PLAYER_TURN;
        }
        return GREEN_PLAYER_TURN;
    }

    public boolean isFinished() {
        return this != GREEN_PLAYER_TURN && this != RED_PLAYER_TURN;
    }

    public String description() {
        return this.description;
    }
}
