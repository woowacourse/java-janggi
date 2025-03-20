package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {

    public Pawn(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        return getMovablePositions().contains(destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        Set<Position> moveablePositions = getMovablePositions();

        List<Piece> pieces = existingPieces.stream()
                .filter(existingPiece -> existingPiece.isSamePosition(destination))
                .filter(existingPiece -> moveablePositions.contains(existingPiece.getPosition()))
                .toList();

        if (pieces.isEmpty()) {
            return true;
        }
        return pieces.getFirst().getSide() != getSide();
    }

    private Set<Position> getMovablePositions() {
        Set<Position> moveablePositions = new HashSet<>();
        if (getXPosition() > 0) {
            moveablePositions.add(new Position(getXPosition() - 1, getYPosition()));
        }
        if (getXPosition() < 8) {
            moveablePositions.add(new Position(getXPosition() + 1, getYPosition()));
        }

        if (getSide() == Side.HAN) {
            moveablePositions.add(new Position(getXPosition(), getYPosition() + 1));
            return moveablePositions;
        }
        moveablePositions.add(new Position(getXPosition(), getYPosition() - 1));
        return moveablePositions;
    }
}
