package application.persistence;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.Piece;
import java.util.Map;

public interface BoardRepository {

    void save(Board board);

    Map<Coordinate, Piece> findAll();

    void deleteAll();
}
