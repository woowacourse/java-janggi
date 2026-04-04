package repository.dao;

import java.sql.Connection;

public interface GamePieceDao {
    void initTable(Connection connection);
    // Other methods will be defined as needed
}
