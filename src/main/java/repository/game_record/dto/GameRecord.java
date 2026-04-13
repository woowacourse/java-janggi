package repository.game_record.dto;

import domain.board.Formation;
import domain.game.GameStatus;

public record GameRecord(
    Long id,
    Formation choFormation,
    Formation hanFormation,
    GameStatus gameStatus
) {

    public GameRecord(Formation choFormation, Formation hanFormation) {
        this(null, choFormation, hanFormation, GameStatus.IN_PROGRESS);
    }
}
