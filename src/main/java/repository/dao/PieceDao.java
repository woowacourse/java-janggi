package repository.dao;

import java.sql.Connection;
import java.util.List;
import repository.entity.Piece;

public interface PieceDao {
    void initTable(Connection connection);

    Long save(Connection connection, Piece entity);

    List<Long> saveAll(Connection connection, List<Piece> entities);

    Piece find(Connection connection, Long id);

    List<Piece> findAll(Connection connection);
}
