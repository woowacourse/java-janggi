package domain.board.repository;

import domain.board.Board;
import java.util.Optional;

public interface BoardRepository {

    void save(final Board board);

    boolean hasAnyPiece();

    Optional<Board> load();

    void deleteAll();
}
