package db.repository;

import core.GameSummary;
import core.JanggiGame;
import db.jdbc.SqlConnection;
import java.util.List;
import java.util.Optional;
import movepolicy.MoveHistory;
import position.Position;

public interface JanggiGameRepository {

    Long save(SqlConnection connection, JanggiGame game);

    Optional<JanggiGame> findById(SqlConnection connection, Long gameId);

    List<GameSummary> findTop10GameRoomsOrderByCreatedAtDesc(SqlConnection connection);

    void update(SqlConnection connection, Long gameId, JanggiGame game);

    void updatePiecePosition(SqlConnection connection, Long gameId, Position departure, Position destination);

    void saveMoveHistory(SqlConnection connection, Long gameId, MoveHistory moveHistory);

    void deletePiece(SqlConnection connection, Long gameId, Position destination);
}
