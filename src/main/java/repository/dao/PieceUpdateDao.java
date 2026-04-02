package repository.dao;

import java.sql.SQLException;

public interface PieceUpdateDao {
    void update(int originRow, int originColumn, int newRow, int newColumn) throws SQLException;
}
