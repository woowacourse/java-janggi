package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Knight extends Piece {

    private static final int VERTICAL_BASE_X_MOVEABLE_DISTANCE = 1;
    private static final int VERTICAL_BASE_Y_MOVEABLE_DISTANCE = 2;

    private static final int HORIZONTAL_BASE_X_MOVEABLE_DISTANCE = 2;
    private static final int HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE = 1;

    public Knight(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        int xDistance = getPosition().getXDistance(destination);
        int yDistance = getPosition().getYDistance(destination);
        return (xDistance == VERTICAL_BASE_X_MOVEABLE_DISTANCE && yDistance == VERTICAL_BASE_Y_MOVEABLE_DISTANCE) ||
                (xDistance == HORIZONTAL_BASE_X_MOVEABLE_DISTANCE && yDistance == HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        List<Piece> onPathPieces = findAllPiecesOnPath(existingPieces, destination);

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.stream()
                    .filter(onPathPiece -> onPathPiece.isSamePosition(destination))
                    .anyMatch(piece -> piece.getSide() != getSide());
        }
        return true;
    }

    private List<Piece> findAllPiecesOnPath(List<Piece> existingPieces, Position destination) {
        Set<Position> path = new HashSet<>(findPaths(destination));

        return existingPieces.stream()
                .filter(existingPiece -> path.contains(existingPiece.getPosition()))
                .toList();
    }

    private List<Position> findPaths(Position destination) {
        if (isVerticalMove(destination)) {
            return findAllVerticalMovablePositions(destination);
        }
        return findAllHorizontalMovablePositions(destination);
    }

    private List<Position> findAllVerticalMovablePositions(Position destination) {
        int x = getXPosition();
        int y = getYPosition();
        int xOffset = getXOffset(destination);
        int yOffset = getYOffset(destination);

        return List.of(
                new Position(x, y + yOffset),
                new Position(x + xOffset, y + 2 * yOffset)
        );
    }

    private List<Position> findAllHorizontalMovablePositions(Position destination) {
        int x = getXPosition();
        int y = getYPosition();
        int xOffset = getXOffset(destination);
        int yOffset = getYOffset(destination);

        return List.of(
                new Position(x + xOffset, y),
                new Position(x + 2 * xOffset, y + yOffset)
        );
    }

    private int getXOffset(Position destination) {
        if (destination.getX() > getXPosition()) {
            return 1;
        }
        return -1;
    }

    private int getYOffset(Position destination) {
        if (destination.getY() > getYPosition()) {
            return 1;
        }
        return -1;
    }

    private boolean isVerticalMove(Position destination) {
        return getPosition().getXDistance(destination) == VERTICAL_BASE_X_MOVEABLE_DISTANCE;
    }
}
