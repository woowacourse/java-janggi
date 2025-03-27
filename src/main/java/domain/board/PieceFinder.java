package domain.board;

import domain.piece.Piece;
import java.util.Optional;

public interface PieceFinder {

    Optional<Piece> findByLocation(BoardLocation boardLocation);
}
