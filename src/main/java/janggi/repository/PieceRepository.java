package janggi.repository;

import janggi.entity.PieceEntity;

import java.sql.Connection;
import java.util.List;

public interface PieceRepository {
    void saveAll(Connection conn, List<PieceEntity> entities);

    void updatePosition(Connection conn, int gameId, int oldRow, int oldCol, int newRow, int newCol);

    void deleteByPosition(Connection conn, int gameId, int row, int col);
}
