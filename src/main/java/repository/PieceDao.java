package repository;

import entity.PieceEntity;

import java.sql.Connection;
import java.util.List;

public interface PieceDao {

    void saveAll(Connection con, List<PieceEntity> pieceEntities);

    void deleteByPosition(Connection con, Long gameId, int row, int col);

    void updatePosition(Connection con, Long gameId, int fromRow, int fromCol, int toRow, int toCol);

    List<PieceEntity> findAllByGameId(Long gameId);
}
