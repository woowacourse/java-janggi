package repository;

import domain.board.Board;
import dto.BoardResponseDto;

public interface BoardRepository {
    void save(Board board);
    BoardResponseDto findAll();
}
