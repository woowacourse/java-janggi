package janggi.domain.piece.movement.fixed;

import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import java.util.Map;

public class KingMovementStrategy implements FixedMovementStrategy {

    @Override
    public boolean isLegalDestination(Position origin, Position destination) {
        return false;
    }

    @Override
    public Pieces getAllPiecesOnPath(Pieces map, Position origin, Position destination) {
        return new Pieces(Map.of());
    }
}
