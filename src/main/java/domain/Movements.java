package domain;

import domain.piece.Piece;
import java.util.List;

public class Movements {
    private final List<Movement> movements;

    public Movements(List<Movement> movements) {
        this.movements = movements;
    }

    public boolean canMoveFromTo(Position startPosition, Position endPosition) {
        return movements.stream()
                .anyMatch(movement -> movement.isValidMove(startPosition, endPosition));
    }

    public List<Position> findIntermediatePositions(Position startPosition, Position endPosition) {
        return movements.stream()
                .filter(movement -> movement.isValidMove(startPosition, endPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."))
                .findIntermediatePositions(startPosition, endPosition);
    }

    public boolean canMovePieceToPosition(Piece target, Position expectedPosition, List<Piece> alivePieces) {
        if (canNotMoveTargetTo(target, expectedPosition)) {
            return false;
        }
        if (hasPieceAtIntermediatePositions(target, expectedPosition, alivePieces)) {
            return false;
        }
        return true;
    }

    private boolean canNotMoveTargetTo(Piece target, Position expectedPosition) {
        return !this.canMoveFromTo(target.getPosition(), expectedPosition);
    }

    private boolean hasPieceAtIntermediatePositions(Piece target, Position expectedPosition, List<Piece> alivePieces) {
        List<Position> intermediatePositions = findIntermediatePositions(target.getPosition(), expectedPosition);
        return hasBlockedPiece(intermediatePositions, alivePieces);
    }

    private boolean hasBlockedPiece(List<Position> intermediatePositions, List<Piece> alivePieces) {
        return intermediatePositions.stream()
                .anyMatch(position -> hasPieceTo(position, alivePieces));
    }

    private boolean hasPieceTo(Position position, List<Piece> alivePieces) {
        return alivePieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(position));
    }
}
