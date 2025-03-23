package domain.board;

import domain.Coordinate;
import domain.piece.Piece;
import java.util.List;
import java.util.Optional;

public interface PieceFinder {

    boolean nonePiecesIn(List<Coordinate> coordinates);

    List<Piece> findPiecesIn(List<Coordinate> coordinates);

    Optional<Piece> findAt(Coordinate coordinate);
}
