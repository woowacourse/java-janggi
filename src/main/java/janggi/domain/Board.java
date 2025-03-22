package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public boolean hasPiece(Position position) {
        return pieces.containsKey(position);
    }

    public boolean isSameSide(Side side, Position position) {
        return getPiece(position).isSameSide(side);
    }

    public void checkMoveablePiece(Side side, Position position) {
        validatePositionExists(position);
        Piece piece = pieces.get(position);
        if (!piece.isSameSide(side)) {
            throw new IllegalArgumentException(ErrorMessage.IS_NOT_SAME_SIDE.getMessage());
        }

        if (piece.generateAvailableMovePositions(this, position).isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }
    }

    private void validatePositionExists(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }

    public void movePiece(Position currentPosition, Position newPosition) {
        Piece piece = getPiece(currentPosition);
        Set<Position> availablePositions = piece.generateAvailableMovePositions(this, currentPosition);

        if (!availablePositions.contains(newPosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
        }

        pieces.remove(currentPosition);
        pieces.put(newPosition, piece);
    }

    public Piece getPiece(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }

        return pieces.get(position);
    }

    public boolean canMoveToPosition(Side side, Position position) {
        return !hasPiece(position) || !isSameSide(side, position);
    }

    public boolean isCannon(Position position) {
        if (!pieces.containsKey(position)) {
            return false;
        }

        return pieces.get(position)
                .isCannon();
    }

    public boolean hasGeneral() {
        return pieces.values()
                .stream()
                .anyMatch(Piece::isGeneral);
    }
}
