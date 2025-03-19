package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Side;
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
        // 가능한 경우의 수 받아온다. List<Position>
        // 해당 포지션에 PieceState가 있다면,, 지금 내 PieceSate와 Side가 같으면 false를 반환해서
        // 움직일 수 있는 경우의 포지션을 다시 재정리하고 그게 newPosition이면 움직인다.
        // 그런데 그런 포지션이 없다면 예외를 반환한다.
        Position currentPosition = pieceState.getPosition();

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
