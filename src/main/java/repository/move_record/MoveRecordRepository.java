package repository.move_record;

import java.util.List;
import repository.move_record.dto.MoveRecord;

public interface MoveRecordRepository {

    void save(MoveRecord moveRecord);

    List<MoveRecord> findAll();

    void deleteAll();
}
