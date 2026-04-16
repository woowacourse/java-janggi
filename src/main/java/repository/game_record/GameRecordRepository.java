package repository.game_record;

import domain.game.GameStatus;
import java.util.List;
import repository.game_record.dto.GameRecord;

public interface GameRecordRepository {

    List<GameRecord> findAllGameRecordsByGameStatus(GameStatus status);

    GameRecord findGameRecordByGameStatus(GameStatus status);

    void save(GameRecord gameRecord);

    void updateGameStatus(Long gameRecordId, GameStatus gameStatus);

    void updateGameStatuses(GameStatus sourceStatus, GameStatus targetStatus);
}
