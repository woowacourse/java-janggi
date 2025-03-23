package janggi.domain.piece;

import janggi.domain.Offset;
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

    private static final List<Offset> UP_RIGHT_PATH = List.of(
        new Offset(0, 1), new Offset(1, 2));
    private static final List<Offset> UP_LEFT_PATH = List.of(
        new Offset(0, 1), new Offset(-1, 2));
    private static final List<Offset> DOWN_RIGHT_PATH = List.of(
        new Offset(0, -1), new Offset(1, -2));
    private static final List<Offset> DOWN_LEFT_PATH = List.of(
        new Offset(0, -1), new Offset(-1, -2));
    private static final List<Offset> RIGHT_UP_PATH = List.of(
        new Offset(1, 0), new Offset(2, 1));
    private static final List<Offset> RIGHT_DOWN_PATH = List.of(
        new Offset(1, 0), new Offset(2, -1));
    private static final List<Offset> LEFT_UP_PATH = List.of(
        new Offset(-1, 0), new Offset(-2, 1));
    private static final List<Offset> LEFT_DOWN_PATH = List.of(
        new Offset(-1, 0), new Offset(-2, -1));

    public Knight(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (position.getXDistance(destination) == VERTICAL_BASE_X_MOVEABLE_DISTANCE) {
            return position.getYDistance(destination) == VERTICAL_BASE_Y_MOVEABLE_DISTANCE;
        }
        if (position.getXDistance(destination) == HORIZONTAL_BASE_X_MOVEABLE_DISTANCE) {
            return position.getYDistance(destination) == HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE;
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
        if (position.getXDistance(destination) == VERTICAL_BASE_X_MOVEABLE_DISTANCE) {
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
            return applyOffsets(UP_RIGHT_PATH);
        }
        return applyOffsets(UP_LEFT_PATH);
    }

    private List<Position> findAllDownwardMovablePositions(Position destination) {
        if (destination.getX() > getXPosition()) {
            return applyOffsets(DOWN_RIGHT_PATH);
        }
        return applyOffsets(DOWN_LEFT_PATH);
    }

    private List<Position> findAllHorizontalMovablePositions(Position destination) {
        if (destination.getX() > getXPosition()) {
            return findAllRightwardMovablePositions(destination);
        }
        return findAllLeftwardMovablePositions(destination);
    }

    private List<Position> findAllRightwardMovablePositions(Position destination) {
        if (destination.getY() > getYPosition()) {
            return applyOffsets(RIGHT_UP_PATH);
        }
        return applyOffsets(RIGHT_DOWN_PATH);
    }

    private List<Position> findAllLeftwardMovablePositions(Position destination) {
        if (destination.getY() > getYPosition()) {
            return applyOffsets(LEFT_UP_PATH);
        }
        return applyOffsets(LEFT_DOWN_PATH);
    }

    private List<Position> applyOffsets(List<Offset> offsets) {
        return offsets.stream()
            .map(offset -> offset.applyTo(position))
            .toList();
    }
}
