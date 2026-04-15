package repository.mapper;

import dao.GameRoomRawData;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.game.Team;
import repository.GameRoomSummary;

public final class GameRoomMapper {

    private GameRoomMapper() {
    }

    public static GameRoomRawData toRawData(long roomId, String roomName, JanggiGame game) {
        return new GameRoomRawData(
                roomId,
                roomName,
                game.currentTurn().name(),
                game.getStatus().name(),
                game.getProgress().consecutivePassCount()
        );
    }

    public static GameRoomSummary toSummary(GameRoomRawData raw) {
        return new GameRoomSummary(
                raw.id(),
                raw.name(),
                Team.valueOf(raw.currentTurn()),
                GameStatus.valueOf(raw.status())
        );
    }
}
