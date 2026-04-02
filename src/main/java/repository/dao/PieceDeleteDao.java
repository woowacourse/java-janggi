package repository.dao;

import java.sql.SQLException;

public interface PieceDeleteDao {
    void delete(int targetRow, int targetColumn) throws SQLException;
}
