package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {

    private static final int MOVABLE_DISTANCE = 1;

    public Pawn(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (!isVerticalMove(destination)) {
            return destination.getXDistance(getPosition()) == MOVABLE_DISTANCE;
        }
        if (getSide() == Side.HAN) {
            return destination.getY() == getYPosition() + MOVABLE_DISTANCE;
        }
        return destination.getY() == getYPosition() - MOVABLE_DISTANCE;
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
            return findAllVerticalMovablePositions();
        }
        return findAllHorizontalMovablePositions(destination);
    }

    private List<Position> findAllVerticalMovablePositions() {
        int x = getXPosition();
        int y = getYPosition();
        if (getSide() == Side.HAN) {
            return List.of(new Position(x, y + MOVABLE_DISTANCE));
        }
        return List.of(new Position(x, y - MOVABLE_DISTANCE));
    }

    private List<Position> findAllHorizontalMovablePositions(Position destination) {
        int x = getXPosition();
        int y = getYPosition();
        if (destination.getX() > getXPosition()) {
            return List.of(new Position(x + MOVABLE_DISTANCE, y));
        }
        return List.of(new Position(x - MOVABLE_DISTANCE, y));
    }

    private boolean isVerticalMove(Position destination) {
        return destination.hasSameX(getPosition());
    }
}
