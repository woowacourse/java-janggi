package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Side;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, PieceState> piecePositions;

    public Board(Map<Position, PieceState> piecePositions) {
        this.piecePositions = piecePositions;
    }

    public String getPieceName(int row, int column) {
        Position position = Position.of(row, column);

        if (!hasPosition(position)) {
            return "#";
        }

        return piecePositions.get(position)
                .toName();
    }

    private boolean hasPosition(Position position) {
        return piecePositions.containsKey(position);
    }

    public PieceState getPiecePosition(Side side, Position position) {
        validatePositionExists(position);
        PieceState pieceState = piecePositions.get(position);
        if (!pieceState.isSameSide(side)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_SAME_SIDE.getMessage());
        }

        return pieceState;
    }

    private void validatePositionExists(Position position) {
        if (!piecePositions.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }

    public void move(PieceState pieceState, Position newPosition) {
        Position currentPosition = pieceState.getPosition();

        List<Position> availablePositions = pieceState.getAvailableMovePositions()
                .stream()
                .filter(position -> {
                    PieceState pieceAtPosition = piecePositions.get(position);
                    return pieceAtPosition == null || !pieceState.isSameSide(pieceAtPosition);
                })
                .toList();

        if (!availablePositions.contains(newPosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }

        pieceState.move(newPosition);

        piecePositions.remove(currentPosition);
        piecePositions.put(newPosition, pieceState);
    }

    @Override
    public String toString() {
        return "Board{" +
                "piecePositions=" + piecePositions +
                '}';
    }
}
