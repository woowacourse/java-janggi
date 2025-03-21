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
        if (getPosition().getXDistance(destination) == VERTICAL_BASE_X_MOVEABLE_DISTANCE) {
            return getPosition().getYDistance(destination) == VERTICAL_BASE_Y_MOVEABLE_DISTANCE;
        }
        if (getPosition().getXDistance(destination) == HORIZONTAL_BASE_X_MOVEABLE_DISTANCE) {
            return getPosition().getYDistance(destination) == HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE;
        }
        return false;
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        List<Position> path = findPath(destination);
        List<Piece> onPathPieces = findAllPiecesOnPath(existingPieces, path);

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.stream()
                    .filter(onPathPiece -> onPathPiece.isSamePosition(destination))
                    .anyMatch(piece -> piece.getSide() != getSide());
        }
        return true;
    }

    private List<Position> findPath(Position destination) {
        if (getPosition().getXDistance(destination) == VERTICAL_BASE_X_MOVEABLE_DISTANCE) {
            return findAllVerticalMovablePositions(destination);
        }
        return findAllHorizontalMovablePositions(destination);
    }

    private List<Piece> findAllPiecesOnPath(List<Piece> existingPieces, List<Position> path) {
        Set<Position> pathSet = new HashSet<>(path);
        return existingPieces.stream()
                .filter(existingPiece -> pathSet.contains(existingPiece.getPosition()))
                .toList();
    }

    private List<Position> findAllVerticalMovablePositions(Position destination) {
        if (destination.getY() > getYPosition()) {
            return findAllUpwardMovablePositions(destination);
        }
        return findAllDownwardMovablePositions(destination);
    }

    private List<Position> findAllUpwardMovablePositions(Position destination) {
        if (destination.getX() > getXPosition()) {
            return List.of(
                    new Position(getXPosition(), getYPosition() + 1),
                    new Position(getXPosition() + 1, getYPosition() + 2)
            );
        }
        return List.of(
                new Position(getXPosition(), getYPosition() + 1),
                new Position(getXPosition() - 1, getYPosition() + 2)
        );
    }

    private List<Position> findAllDownwardMovablePositions(Position destination) {
        if (destination.getX() > getXPosition()) {
            return List.of(
                    new Position(getXPosition(), getYPosition() - 1),
                    new Position(getXPosition() + 1, getYPosition() - 2)
            );
        }
        return List.of(
                new Position(getXPosition(), getYPosition() - 1),
                new Position(getXPosition() - 1, getYPosition() - 2)
        );
    }

    private List<Position> findAllHorizontalMovablePositions(Position destination) {
        if (destination.getX() > getXPosition()) {
            return findAllRightwardMovablePositions(destination);
        }
        return findAllLeftwardMovablePositions(destination);
    }

    private List<Position> findAllRightwardMovablePositions(Position destination) {
        if (destination.getY() > getYPosition()) {
            return List.of(
                    new Position(getXPosition() + 1, getYPosition()),
                    new Position(getXPosition() + 2, getYPosition() + 1)
            );
        }
        // 오른쪽 아래 이동
        return List.of(
                new Position(getXPosition() + 1, getYPosition()),
                new Position(getXPosition() + 2, getYPosition() - 1)
        );
    }

    private List<Position> findAllLeftwardMovablePositions(Position destination) {
        if (destination.getY() > getYPosition()) {
            return List.of(
                    new Position(getXPosition() - 1, getYPosition()),
                    new Position(getXPosition() - 2, getYPosition() + 1)
            );
        }
        // 왼쪽 아래 이동
        return List.of(
                new Position(getXPosition() - 1, getYPosition()),
                new Position(getXPosition() - 2, getYPosition() - 1)
        );
    }
}
