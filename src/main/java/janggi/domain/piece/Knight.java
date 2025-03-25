package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

public class Knight extends Piece {

    private static final List<Vector> VERTICAL_MOVEMENT_VECTORS = List.of(
        new Vector(0, 1), new Vector(1, 2));
    private static final List<Vector> HORIZONTAL_MOVEMENT_VECTORS = List.of(
        new Vector(1, 0), new Vector(2, 1));

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
        return isVerticalMove(destination) || isHorizontalMove(destination);
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

    private List<Piece> findAllPiecesOnPath(List<Piece> existingPieces, List<Position> path) {
        return existingPieces.stream()
            .filter(piece -> path.contains(piece.getPosition()))
            .toList();
    }

    private List<Position> findPath(Position destination) {
        if (isVerticalMove(destination)) {
            return findAllMovablePositions(destination, VERTICAL_MOVEMENT_VECTORS);
        }
        return findAllMovablePositions(destination, HORIZONTAL_MOVEMENT_VECTORS);
    }

    private List<Position> findAllMovablePositions(Position destination, List<Vector> movePatterns) {
        return movePatterns.stream()
            .map(pattern -> pattern.apply(position, destination))
            .toList();
    }

    private boolean isVerticalMove(Position destination) {
        return VERTICAL_MOVEMENT_VECTORS.getLast().hasRelativeOffsetFrom(position, destination);
    }

    private boolean isHorizontalMove(Position destination) {
        return HORIZONTAL_MOVEMENT_VECTORS.getLast().hasRelativeOffsetFrom(position, destination);
    }
}
