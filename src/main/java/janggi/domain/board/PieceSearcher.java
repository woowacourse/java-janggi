package janggi.domain.board;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import java.util.List;
import java.util.Optional;

public interface PieceSearcher {

    boolean nonePiecesIn(List<Coordinate> coordinates);

    List<Piece> findPiecesIn(List<Coordinate> coordinates);

    Optional<Piece> findAt(Coordinate coordinate);
}
