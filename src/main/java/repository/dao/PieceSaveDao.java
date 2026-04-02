package repository.dao;

import java.sql.SQLException;
import java.util.List;
import repository.entity.PieceEntity;

public interface PieceSaveDao {
    void save(PieceEntity entity) throws SQLException;

    void saveAll(List<PieceEntity> entities) throws SQLException;
}
