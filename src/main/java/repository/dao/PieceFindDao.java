package repository.dao;

import java.sql.SQLException;
import java.util.List;
import repository.entity.PieceEntity;

public interface PieceFindDao {
    PieceEntity find(int row, int column) throws SQLException;

    List<PieceEntity> findAll() throws SQLException;
}
