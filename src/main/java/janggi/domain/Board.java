package janggi.domain;

import janggi.common.ErrorMessage;
import java.util.Map;
import java.util.Set;

public class Board {

    private final Map<Position, Piece> pieceMap;

    public Board(Map<Position, Piece> pieceMap) {
        this.pieceMap = pieceMap;
    }

    public String getPieceName(int row, int column) {
        Position position = Position.of(row, column);

        if (!hasPiece(position)) {
            return "#";
        }

        return pieceMap.get(position)
                .toName();
    }

    public boolean hasPiece(Position position) {
        return pieceMap.containsKey(position);
    }

    public void checkMoveablePiece(Side side, Position position) {
        validatePositionExists(position);
        Piece piece = pieceMap.get(position);
        if (!piece.isSameSide(side) ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_SAME_SIDE.getMessage());
        }

        if(piece.getAvailableMovePositions(this, position).isEmpty()){
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }
    }

    private void validatePositionExists(Position position) {
        if (!pieceMap.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }

    public void move(Position currentPosition, Position newPosition) {
        Piece piece = getPiece(currentPosition);
        Set<Position> availablePositions = piece.getAvailableMovePositions(this, currentPosition);

        if (!availablePositions.contains(newPosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
        }


        pieceMap.remove(currentPosition);
        pieceMap.put(newPosition, piece);
    }

    public boolean isSameSide(Side side, Position position) {
        // 해당 포지션에 적 팀이거나 빈 공간이라면 갈 수 있다.
        return getPiece(position).isSameSide(side);
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

        return pieceMap.get(position)
                .isCannon();
    }
}
