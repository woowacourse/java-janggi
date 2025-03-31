package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class Cannon extends Piece {

    private static final double SCORE = 7.0;

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
    public double getScore() {
        return SCORE;
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
        List<Piece> pathPieces = getPiecesOnPath(existingPieces, destination);

        if (!isValidCannonPath(pathPieces)) {
            return false;
        }
        return canCapture(existingPieces, destination);
    }

    private boolean isValidCannonPath(List<Piece> pathPieces) {
        return pathPieces.size() == 1 && !pathPieces.getFirst().isCannon();
    }

    private boolean canCapture(List<Piece> existingPieces, Position destination) {
        Optional<Piece> targetPiece = findPiece(existingPieces, destination);
        return targetPiece.isEmpty() || (targetPiece.get().side != this.side && !targetPiece.get().isCannon());
    }

    private Optional<Piece> findPiece(List<Piece> existingPieces, Position position) {
        return existingPieces.stream()
            .filter(piece -> piece.isSamePosition(position))
            .findAny();
    }

    private List<Piece> getPiecesOnPath(List<Piece> existingPieces, Position destination) {
        if (position.hasSameX(destination)) {
            return getPiecesBetween(
                existingPieces, position.getY(), destination.getY(),
                y -> new Position(getXPosition(), y)
            );
        }
        if (position.hasSameY(destination)) {
            return getPiecesBetween(
                existingPieces, position.getX(), destination.getX(),
                x -> new Position(x, getYPosition())
            );
        }
        return getPiecesOnPalaceCenter(existingPieces, destination);
    }

    private List<Piece> getPiecesBetween(List<Piece> existingPieces, int start, int end,
                                         IntFunction<Position> positionMapper) {
        return IntStream.range(Math.min(start, end) + 1, Math.max(start, end))
            .mapToObj(positionMapper)
            .flatMap(pos -> findPiece(existingPieces, pos).stream())
            .toList();
    }

    private List<Piece> getPiecesOnPalaceCenter(List<Piece> existingPieces, Position destination) {
        Position palaceCenter = new Position(
            Math.max(destination.getX(), position.getX()) - 1,
            Math.max(destination.getY(), position.getY()) - 1
        );
        return findPiece(existingPieces, palaceCenter).map(List::of).orElse(List.of());
    }
}
