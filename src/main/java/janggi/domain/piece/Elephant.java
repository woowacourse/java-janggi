package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Elephant extends Piece {

    private static final int VERTICAL_BASE_X_MOVEABLE_DISTANCE = 2;
    private static final int VERTICAL_BASE_Y_MOVEABLE_DISTANCE = 3;

    private static final int HORIZONTAL_BASE_X_MOVEABLE_DISTANCE = 3;
    private static final int HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE = 2;

    public Elephant(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        Position position = getPosition();

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
        List<Piece> onPathPieces = findAllPiecesOnPath(existingPieces);

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.stream()
                    .filter(onPathPiece -> onPathPiece.isSamePosition(destination))
                    .anyMatch(onPathPiece -> onPathPiece.getSide() != getSide());
        }
        return true;
    }

    private List<Piece> findAllPiecesOnPath(List<Piece> existingPieces) {
        Set<Position> moveablePositions = findAllMoveablePositions();
        return existingPieces.stream()
                .filter(existingPiece -> moveablePositions.contains(existingPiece.getPosition()))
                .toList();
    }

    private Set<Position> findAllMoveablePositions() {
        Set<Position> moveablePositions = new HashSet<>();
        addAllHorizontalMoveablePositions(moveablePositions, getXPosition(), getYPosition());
        addAllVerticalMoveablePositions(moveablePositions, getXPosition(), getYPosition());

        return moveablePositions;
    }

    private void addAllHorizontalMoveablePositions(Set<Position> moveablePositions, int currentX, int currentY) {
        IntStream.rangeClosed(1, HORIZONTAL_BASE_X_MOVEABLE_DISTANCE)
                .forEach(
                        x -> IntStream.rangeClosed(0, HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE)
                                .forEach(y -> moveablePositions.addAll(
                                        findAllAvailablePositions(currentX, currentY, x, y))
                                )
                );
    }

    private void addAllVerticalMoveablePositions(Set<Position> moveablePositions, int currentX, int currentY) {
        IntStream.rangeClosed(1, VERTICAL_BASE_Y_MOVEABLE_DISTANCE)
                .forEach(
                        y -> IntStream.rangeClosed(0, VERTICAL_BASE_X_MOVEABLE_DISTANCE)
                                .forEach(x -> moveablePositions.addAll(
                                        findAllAvailablePositions(currentX, currentY, x, y))
                                )
                );
    }

    private Set<Position> findAllAvailablePositions(int currentX, int currentY, int x, int y) {
        Set<Position> result = new HashSet<>();
        addPositionIfPossible(result, currentX - x, currentY - y);
        addPositionIfPossible(result, currentX + x, currentY + y);
        addPositionIfPossible(result,currentX + x, currentY - y);
        addPositionIfPossible(result,currentX - x, currentY + y);
        return result;
    }

    private void addPositionIfPossible(Set<Position> result, int x, int y) {
        try {
            result.add(new Position(x, y));
        } catch (IllegalArgumentException ignored) {

        }
    }
}
