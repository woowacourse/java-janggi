package repository;

import domain.board.Board;
import dto.BoardResponseDto;

public interface BoardRepository {
    void save(Long gameId, Board board);
    BoardResponseDto findByGameId(Long gameId);
}
