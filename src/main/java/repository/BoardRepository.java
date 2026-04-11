package repository;

import domain.board.Board;
import domain.piece.Side;
import domain.position.Position;

import java.sql.Connection;
import java.util.Optional;

public interface BoardRepository {
    Optional<Board> findByGameId(Long gameId);

    void savePlacementByGameId(Board board, Long gameId, Side side);
    void savePlacementByGameId(Board board, Long gameId, Side side, Connection connection);

    void updatePiecePositionByGameId(Position from, Position to, Long gameId);
    void updatePiecePositionByGameId(Position from, Position to, Long gameId, Connection connection);

    void deletePiecePositionByGameId(Position from, Long gameId);
    void deletePiecePositionByGameId(Position from, Long gameId, Connection connection);
}
