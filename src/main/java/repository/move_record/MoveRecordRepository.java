package repository.move_record;

import java.util.List;

public interface MoveRecordRepository {

    void save(MoveRecord moveRecord);

    List<MoveRecord> findAll();

    void deleteAll();
}
