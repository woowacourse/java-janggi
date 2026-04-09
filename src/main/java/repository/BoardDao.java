package repository;

import entity.BoardEntity;

import java.sql.Connection;
import java.util.List;

public interface BoardDao {

    void saveAll(Connection con, List<BoardEntity> pieceEntities);

    void deleteByPosition(Connection con, Long gameId, int row, int col);

    void updatePosition(Connection con, Long gameId, int fromRow, int fromCol, int toRow, int toCol);

    List<BoardEntity> findAllByGameId(Long gameId);
}
