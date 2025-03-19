package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elephant extends Piece {

    private static final int VERTICAL_BASE_X_MOVEABLE_DISTANCE = 2;
    private static final int VERTICAL_BASE_Y_MOVEABLE_DISTANCE = 3;

    private static final int HORIZONTAL_BASE_X_MOVEABLE_DISTANCE = 3;
    private static final int HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE = 2;

    public Elephant(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(int x, int y) {
        Position position = getPosition();

        if (Math.abs(position.getX() - x) == VERTICAL_BASE_X_MOVEABLE_DISTANCE) {
            return Math.abs(position.getY() - y) == VERTICAL_BASE_Y_MOVEABLE_DISTANCE;
        }
        if (Math.abs(position.getX() - x) == HORIZONTAL_BASE_X_MOVEABLE_DISTANCE) {
            return Math.abs(position.getY() - y) == HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE;
        }
        return false;
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, int x, int y) {
        Set<Position> moveablePositions = findAllMoveablePositions();
        for (Piece existingPiece : existingPieces) {
            if (moveablePositions.contains(existingPiece.getPosition())) {
                if (existingPiece.getPosition().isSameCoordinate(x, y)) {
                    if (existingPiece.getSide() == getSide()) {
                        return false;
                    }
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    private Set<Position> findAllMoveablePositions() {
        Set<Position> moveablePositions = new HashSet<>();
        Position position = getPosition();
        int x = position.getX();
        int y = position.getY();
        moveablePositions.add(new Position(x, y + 1));
        moveablePositions.add(new Position(x - 1, y + 2));
        moveablePositions.add(new Position(x - 2, y + 3));

        moveablePositions.add(new Position(x + 1, y + 2));
        moveablePositions.add(new Position(x + 2, y + 3));

        moveablePositions.add(new Position(x + 1, y));
        moveablePositions.add(new Position(x + 2, y + 1));
        moveablePositions.add(new Position(x + 3, y + 2));

        moveablePositions.add(new Position(x + 2, y - 1));
        moveablePositions.add(new Position(x + 3, y - 2));

        moveablePositions.add(new Position(x, y - 1));
        moveablePositions.add(new Position(x + 1, y - 2));
        moveablePositions.add(new Position(x + 2, y - 3));

        moveablePositions.add(new Position(x - 1, y - 2));
        moveablePositions.add(new Position(x - 2, y - 3));

        moveablePositions.add(new Position(x - 1, y));
        moveablePositions.add(new Position(x - 2, y -1));
        moveablePositions.add(new Position(x - 3, y - 2));

        moveablePositions.add(new Position(x - 2, y + 1));
        moveablePositions.add(new Position(x - 3, y + 2));

        return moveablePositions;
    }
}
