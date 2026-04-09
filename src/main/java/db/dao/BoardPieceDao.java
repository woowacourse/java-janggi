package db.dao;

import db.jdbc.SqlConnection;
import db.model.BoardPieceEntity;
import java.util.List;
import java.util.Optional;

public interface BoardPieceDao {

    void saveAll(SqlConnection connection, List<BoardPieceEntity> boardPieces);

    List<BoardPieceEntity> findAllByGameId(SqlConnection connection, Long gameId);

    Optional<BoardPieceEntity> findByGameIdAndPosition(SqlConnection connection, Long gameId, int row, int column);

    void updatePosition(SqlConnection connection, Long id, int row, int column);

    void deleteById(SqlConnection connection, Long id);
}
