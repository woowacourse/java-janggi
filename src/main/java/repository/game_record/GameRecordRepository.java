package repository.game_record;

import repository.game_record.dto.GameRecord;

public interface GameRecordRepository {

    boolean existsGameRecord();

    GameRecord findGameRecord();

    void save(GameRecord gameRecord);

    void deleteAll();
}
