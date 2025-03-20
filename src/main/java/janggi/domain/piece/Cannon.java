package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;
import java.util.stream.IntStream;

public class Cannon extends Piece {

    public Cannon(Side side, int x, int y) {
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
        if (hasNoObstacle(existingPieces, destination)) {
            return existingPieces.stream()
                    .filter(piece -> piece.isSamePosition(destination))
                    .map(piece -> piece.getSide() != getSide() && !piece.getClass().equals(this.getClass()))
                    .findAny()
                    .orElse(true);
        }
        return false;
    }

    private Piece findByPosition(List<Piece> existingPieces, Position position) {
        return existingPieces.stream()
                .filter(existingPiece -> existingPiece.isSamePosition(position))
                .findAny()
                .orElse(null);
    }

    private boolean hasNoObstacle(List<Piece> existingPieces, Position destination) {
        List<Piece> piecesOnPath = getPiecesOnPath(existingPieces, destination);
        if (piecesOnPath.size() != 1) {
            return false;
        }
        return piecesOnPath.stream()
                .noneMatch(piece -> piece.getClass().equals(this.getClass()));
    }

    private boolean hasPosition(List<Piece> existingPieces, Position position) {
        return existingPieces.stream()
                .anyMatch(existingPiece -> existingPiece.isSamePosition(position));
    }

    private List<Piece> getPiecesOnPath(List<Piece> existingPieces, Position destination) {
        if (getPosition().hasSameX(destination)) {
            return getPiecesOnVerticalPath(existingPieces, destination.getY());
        }
        return getPiecesOnHorizontalPath(existingPieces, destination.getX());
    }

    private List<Piece> getPiecesOnHorizontalPath(List<Piece> existingPieces, int destinationX) {
        return IntStream.range(Math.min(getXPosition(), destinationX) + 1, Math.max(getXPosition(), destinationX))
                .filter(x -> hasPosition(existingPieces, new Position(x, getYPosition())))
                .mapToObj(x -> findByPosition(existingPieces, new Position(x, getYPosition())))
                .toList();
    }

    private List<Piece> getPiecesOnVerticalPath(List<Piece> existingPieces, int destinationY) {
        return IntStream.range(Math.min(getYPosition(), destinationY) + 1, Math.max(getYPosition(), destinationY))
                .filter(y -> hasPosition(existingPieces, new Position(getXPosition(), y)))
                .mapToObj(y -> findByPosition(existingPieces, new Position(getXPosition(), y)))
                .toList();
    }
}
