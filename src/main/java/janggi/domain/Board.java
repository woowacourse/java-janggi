package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Side;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, PieceState> pieceStates;

    public Board(Map<Position, PieceState> pieceStates) {
        this.pieceStates = pieceStates;
    }

    public String getPieceName(int row, int column) {
        Position position = Position.of(row, column);

        if (!hasPosition(position)) {
            return "#";
        }

        return pieceStates.get(position)
                .toName();
    }

    public boolean hasPosition(Position position) {
        return pieceStates.containsKey(position);
    }

    public PieceState getPiecePositionBySameSide(Side side, Position position) {
        validatePositionExists(position);
        PieceState pieceState = pieceStates.get(position);
        if (!pieceState.isSameSide(side)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_SAME_SIDE.getMessage());
        }

        return pieceState;
    }

    private void validatePositionExists(Position position) {
        if (!pieceStates.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }

    public void move(PieceState pieceState, Position newPosition) {
        Position currentPosition = pieceState.getPosition();

        List<Position> availablePositions = pieceState.getAvailableMovePositions()
                .stream()
                .filter(position -> {
                    PieceState pieceAtPosition = pieceStates.get(position);
                    return pieceAtPosition == null || !pieceState.isSameSide(pieceAtPosition);
                })
                .toList();

        if (!availablePositions.contains(newPosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }

        pieceState.move(newPosition);

        pieceStates.remove(currentPosition);
        pieceStates.put(newPosition, pieceState);
    }

    public boolean isSameSide(Side side, Position position) {
        // 해당 포지션에 적 팀이거나 빈 공간이라면 갈 수 있다.
        return getPieceState(position).isSameSide(side);
    }

    private PieceState getPieceState(Position position) {
        if (!pieceStates.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }

        return pieceStates.get(position);
    }

    @Override
    public String toString() {
        return "Board{" +
                "piecePositions=" + pieceStates +
                '}';
    }
}
