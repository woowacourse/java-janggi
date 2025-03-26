package janggi.domain.piece.movement.fixed;

import janggi.domain.piece.pieces.Pieces;
import janggi.domain.piece.pieces.PiecesView;
import janggi.domain.piece.Position;
import java.util.Map;

public class KingMovementStrategy extends FixedMovementStrategy {

    @Override
    public boolean isLegalDestination(Position origin, Position destination) {
        return false;
    }

    @Override
    public PiecesView getAllPiecesOnPath(PiecesView map, Position origin, Position destination) {
        return new Pieces(Map.of());
    }
}
