package game.domain.board;

import game.domain.piece.Piece;
import java.util.Optional;

public interface PieceFinder {

    Optional<Piece> findByLocation(BoardLocation boardLocation);
}
