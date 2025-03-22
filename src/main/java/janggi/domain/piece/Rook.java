package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.HashSet;
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
        int start = Math.min(getYPosition(), destination.getY()) + 1;
        int end = Math.max(getYPosition(), destination.getY());

        return IntStream.rangeClosed(start, end)
                .mapToObj(y -> new Position(getXPosition(), y))
                .toList();
    }

    private List<Position> findAllHorizontalMovablePositions(Position destination) {
        int start = Math.min(getXPosition(), destination.getX()) + 1;
        int end = Math.max(getXPosition(), destination.getX());

        return IntStream.rangeClosed(start, end)
                .mapToObj(x -> new Position(x, getYPosition()))
                .toList();
    }

    private boolean isVerticalMove(Position destination) {
        return destination.hasSameX(getPosition());
    }
}
