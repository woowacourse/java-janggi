package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.movement.fixed.ElephantMovementStrategy;
import java.util.List;

public class Elephant extends Piece {

    private final ElephantMovementStrategy movement = new ElephantMovementStrategy();

    public Elephant(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        return movement.isLegalDestination(getPosition(), destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        return movement.isPathClear(Pieces.from(existingPieces), getSide(), getPosition(), destination);
    }
}
