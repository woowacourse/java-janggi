package domain.board.repository;

import domain.board.Board;

public interface BoardRepository {

    void save(final Board board);

    boolean hasAnyPiece();

    Board load();

    void deleteAll();
}
