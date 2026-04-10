package repository;

import domain.board.Board;

import java.sql.Connection;
import java.util.Optional;

public interface BoardRepository {
    void save(Connection connection, Long gameId, Board board);
    Optional<Board> findByGameId(Long gameId);
}
