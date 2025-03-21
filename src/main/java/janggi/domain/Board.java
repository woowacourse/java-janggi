package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import java.util.Map;
import java.util.Set;

public class Board {

    private final Map<Position, Piece> pieceMap;

    public Board(Map<Position, Piece> pieceMap) {
        validate(pieceMap);
        this.pieceMap = pieceMap;
    }

    public void validate(Map<Position, Piece> pieceMap) {
        if (pieceMap == null) {
            throw new IllegalArgumentException();
        }
    }

    public boolean hasPiece(Position position) {
        return pieceMap.containsKey(position);
    }

    public boolean isSameSide(Side side, Position position) {
        return getPiece(position).isSameSide(side);
    }

    public void checkMoveablePiece(Side side, Position position) {
        validatePositionExists(position);
        Piece piece = pieceMap.get(position);
        if (!piece.isSameSide(side)) {
            throw new IllegalArgumentException(ErrorMessage.IS_NOT_SAME_SIDE.getMessage());
        }

        if (piece.getAvailableMovePositions(this, position).isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }
    }

    private void validatePositionExists(Position position) {
        if (!pieceMap.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }

    public void movePiece(Position currentPosition, Position newPosition) {
        Piece piece = getPiece(currentPosition);
        Set<Position> availablePositions = piece.getAvailableMovePositions(this, currentPosition);

        if (!availablePositions.contains(newPosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
        }

        pieceMap.remove(currentPosition);
        // TODO PUT을 할 때 기존의 POSITION의 키가 덮어씌워진다는 것을 표현할 수 없다.
        // 사라진 다는 것을 표현하면 좋을 거 같다.
        pieceMap.put(newPosition, piece);
    }

    public Piece getPiece(Position position) {
        if (!pieceMap.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }

        return pieceMap.get(position);
    }

    public boolean canMoveToPosition(Side side, Position position) {
        return !hasPiece(position) || !isSameSide(side, position);
    }

    public boolean isCannon(Position position) {
        if (!pieceMap.containsKey(position)) {
            return false;
        }

        return pieceMap.get(position).isCannon();
    }

    public boolean hasGeneral(Side side) {
        return pieceMap.values().stream().anyMatch(piece -> piece.isGeneral(side));
    }

    public String getPieceName(int row, int column) {
        Position position = Position.of(row, column);

        if (!hasPiece(position)) {
            return "＿";
        }

        return pieceMap.get(position).toName();
    }
}
