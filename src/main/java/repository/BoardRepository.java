package repository;

import domain.board.Board;

import java.util.Optional;

public interface BoardRepository {
    void save(Long gameId, Board board);
    Optional<Board> findByGameId(Long gameId);
}
