package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Piece;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public boolean canMovePiece(Side currentTurn, Position selectedPosition, Position targetPosition) {
        validatePositionExists(selectedPosition);
        validateCurrentTurn(selectedPosition, currentTurn);
        validateTargetPiece(selectedPosition, targetPosition);

        Piece selectedPiece = getPiece(selectedPosition);
        return selectedPiece.canMove(Collections.unmodifiableMap(pieces), selectedPosition, targetPosition);
    }

    private void validatePositionExists(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }

    private void validateCurrentTurn(Position selectedPosition, Side turn) {
        Piece selectedPiece = getPiece(selectedPosition);
        if (!selectedPiece.isSameSide(turn)) {
            throw new IllegalArgumentException(ErrorMessage.IS_NOT_SAME_SIDE.getMessage());
        }
    }

    private void validateTargetPiece(Position selectedPosition, Position targetPosition) {
        if (!pieces.containsKey(targetPosition)) {
            return;
        }

        Piece selectedPiece = getPiece(selectedPosition);
        Piece targetPiece = getPiece(targetPosition);
        if (selectedPiece.equals(targetPiece) || selectedPiece.isSameSide(targetPiece)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
        }
    }

    public Piece getPiece(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }
        return pieces.get(position);
    }

    public void movePiece(Position selectedPosition, Position targetPosition) {
        Piece selectedPiece = getPiece(selectedPosition);
        updatePosition(selectedPosition, targetPosition, selectedPiece);
    }

    private void updatePosition(Position selectedPosition, Position targetPosition, Piece selectedPiece) {
        pieces.remove(selectedPosition);
        pieces.put(targetPosition, selectedPiece);
    }

    public boolean hasPiece(Position position) {
        return pieces.containsKey(position);
    }

    public boolean hasGeneral() {
        return pieces.values()
                .stream()
                .anyMatch(Piece::isGeneral);
    }
}
