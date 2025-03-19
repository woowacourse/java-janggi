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
        List<Piece> onPathPieces = findAllPiecesOnPath(existingPieces);

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.stream()
                    .filter(onPathPiece -> onPathPiece.getPosition().isSameCoordinate(x, y))
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
        result.add(new Position(currentX - x, currentY - y));
        result.add(new Position(currentX + x, currentY + y));
        result.add(new Position(currentX + x, currentY - y));
        result.add(new Position(currentX - x, currentY + y));
        return result;
    }
}
