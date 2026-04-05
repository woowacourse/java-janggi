package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.List;
import java.util.Optional;

public interface Movement {

    Optional<List<Location>> calculateRoute(Location from, Location to);

    void detectCollision(Side side, List<Piece> piecesOnPath);
}
