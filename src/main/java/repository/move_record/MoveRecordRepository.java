package repository.move_record;

import java.util.List;
import repository.move_record.dto.MoveRecord;

public interface MoveRecordRepository {

    void save(Long gameRecordId, MoveRecord moveRecord);

    List<MoveRecord> findAllByGameRecordId(Long gameRecordId);
}
