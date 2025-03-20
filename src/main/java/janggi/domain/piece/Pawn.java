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

        return existingPieces.stream()
                .filter(existingPiece -> existingPiece.isSamePosition(destination))
                .filter(existingPiece -> moveablePositions.contains(existingPiece.getPosition()))
                .anyMatch(existingPiece -> existingPiece.getSide() != getSide());
    }

    private Set<Position> getMovablePositions() {
        Set<Position> moveablePositions = new HashSet<>();
        moveablePositions.add(new Position(getXPosition() - 1, getYPosition()));
        moveablePositions.add(new Position(getXPosition() + 1, getYPosition()));

        if (getSide() == Side.HAN) {
            moveablePositions.add(new Position(getXPosition(), getYPosition() + 1));
            return moveablePositions;
        }
        moveablePositions.add(new Position(getXPosition(), getYPosition() - 1));
        return moveablePositions;
    }
}
