package repository;

import domain.board.Board;
import dto.BoardResponseDto;

import java.util.Optional;

public interface BoardRepository {
    void save(Long gameId, Board board);
    Optional<BoardResponseDto> findByGameId(Long gameId);
}
