package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.movement.dynamic.DynamicMovementStrategy;
import janggi.domain.piece.movement.dynamic.PawnMovementStrategy;
import java.util.List;

public class Pawn extends Piece {

    private final DynamicMovementStrategy movement = new PawnMovementStrategy();

    public Pawn(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        return movement.isLegalDestination(getSide(), getPosition(), destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        return movement.isLegalPath(Pieces.from(existingPieces), getSide(), getPosition(), destination);
    }
}
