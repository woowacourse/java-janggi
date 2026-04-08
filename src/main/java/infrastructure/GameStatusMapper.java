package infrastructure;

import domain.GameStatus;
import domain.Team;
import java.util.Map;

public class GameStatusMapper {

    private static final Map<GameStatus, String> CURRENT_TURN_MAP = Map.of(
            GameStatus.GREEN_PLAYER_TURN, Team.GREEN.name(),
            GameStatus.GREEN_TEAM_WIN, Team.GREEN.name(),
            GameStatus.RED_PLAYER_TURN, Team.RED.name(),
            GameStatus.RED_TEAM_WIN, Team.RED.name()
    );

    private static final Map<GameStatus, String> WINNER_MAP = Map.of(
            GameStatus.GREEN_TEAM_WIN, Team.GREEN.name(),
            GameStatus.RED_TEAM_WIN, Team.RED.name()
    );

    private GameStatusMapper() {
    }

    public static String toCurrentTurn(GameStatus status) {
        return CURRENT_TURN_MAP.get(status);
    }

    public static String toWinner(GameStatus status) {
        return WINNER_MAP.get(status);
    }
}
