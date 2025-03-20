package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Rook extends Piece {

    public Rook(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (getPosition().hasSameX(destination)) {
            return !getPosition().hasSameY(destination);
        }
        return getPosition().hasSameY(destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        Set<Position> positionsTo = Set.copyOf(getPositionsTo(destination));
        for (Piece existingPiece : existingPieces) {
            if (positionsTo.contains(existingPiece.getPosition())) {
                return false;
            }
            if (existingPiece.isSamePosition(destination)) {
                return !existingPiece.getSide().equals(getSide());
            }
        }
        return true;
    }

    private List<Position> getPositionsTo(Position destination) {
        if (hasSameX(destination)) {
            if (getYPosition() < destination.getY()) {
                return IntStream.range(getYPosition() + 1, destination.getY())
                        .mapToObj(y -> new Position(getXPosition(), y))
                        .toList();
            }
            return IntStream.range(destination.getY() + 1, getYPosition())
                    .mapToObj(y -> new Position(getXPosition(), y))
                    .toList();
        }
        if (getXPosition() < destination.getX()) {
            return IntStream.range(getXPosition() + 1, destination.getX())
                    .mapToObj(x -> new Position(x, getYPosition()))
                    .toList();
        }
        return IntStream.range(destination.getX() + 1, getXPosition())
                .mapToObj(x -> new Position(x, getYPosition()))
                .toList();
    }

    private boolean hasSameX(Position position) {
        return getXPosition() == position.getX();
    }
}
