package janggi.domain.piece;

import janggi.domain.Side;

import java.util.List;
import java.util.stream.IntStream;

public class Cannon extends Piece {

    public Cannon(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (position.hasSameX(destination) || position.hasSameY(destination)) {
            return true;
        }
        if (position.isPalaceCorner() && destination.isPalaceCorner()) {
            return position.isDiagnose(destination);
        }
        return false;
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        List<Piece> piecesOnPath = getPiecesOnPath(existingPieces, destination);

        if (!isValidPath(piecesOnPath)) {
            return false;
        }
        if (!hasSamePosition(existingPieces, destination)) {
            return true;
        }
        Piece piece = findByPosition(existingPieces, destination);
        return piece.side != this.side && !piece.isCannon();
    }

    private boolean isValidPath(List<Piece> piecesOnPath) {
        return hasOnlyOnePiece(piecesOnPath) && hasNoCannon(piecesOnPath);
    }

    private boolean hasOnlyOnePiece(List<Piece> pieces) {
        return pieces.size() == 1;
    }

    private boolean hasNoCannon(List<Piece> pieces) {
        return pieces.stream()
            .noneMatch(Piece::isCannon);
    }

    private boolean hasSamePosition(List<Piece> existingPieces, Position position) {
        return existingPieces.stream()
            .anyMatch(existingPiece -> existingPiece.isSamePosition(position));
    }

    private Piece findByPosition(List<Piece> existingPieces, Position position) {
        return existingPieces.stream()
            .filter(existingPiece -> existingPiece.isSamePosition(position))
            .findAny()
            .get();
    }

    private List<Piece> getPiecesOnPath(List<Piece> existingPieces, Position destination) {
        if (position.hasSameX(destination)) {
            return getPiecesOnVerticalPath(existingPieces, destination.getY());
        }
        if (position.hasSameY(destination)) {
            return getPiecesOnHorizontalPath(existingPieces, destination.getX());
        }
        return getPiecesOnPalaceCenter(existingPieces, destination);
    }

    private List<Piece> getPiecesOnHorizontalPath(List<Piece> existingPieces, int destinationX) {
        return IntStream.range(Math.min(getXPosition(), destinationX) + 1, Math.max(getXPosition(), destinationX))
            .filter(x -> hasSamePosition(existingPieces, new Position(x, getYPosition())))
            .mapToObj(x -> findByPosition(existingPieces, new Position(x, getYPosition())))
            .toList();
    }

    private List<Piece> getPiecesOnVerticalPath(List<Piece> existingPieces, int destinationY) {
        return IntStream.range(Math.min(getYPosition(), destinationY) + 1, Math.max(getYPosition(), destinationY))
            .filter(y -> hasSamePosition(existingPieces, new Position(getXPosition(), y)))
            .mapToObj(y -> findByPosition(existingPieces, new Position(getXPosition(), y)))
            .toList();
    }

    private List<Piece> getPiecesOnPalaceCenter(List<Piece> existingPieces, Position destination) {
        int x = Math.max(destination.getX(), position.getX()) - 1;
        int y = Math.max(destination.getY(), position.getY()) - 1;
        Position palaceCenter = new Position(x, y);

        return existingPieces.stream()
            .filter(piece -> piece.isSamePosition(palaceCenter))
            .toList();
    }
}
