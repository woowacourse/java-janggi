package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.PiecePosition;
import janggi.domain.piece.Side;
import java.util.Map;

public class Board {

    private final Map<Position, PiecePosition> piecePositions;

    public Board(Map<Position, PiecePosition> piecePositions) {
        this.piecePositions = piecePositions;
    }

    public String getPieceName(int row, int column) {
        Position position = new Position(row, column);

        if (!hasPosition(position)) {
            return "#";
        }

        return piecePositions.get(position)
                .toName();
    }

    private boolean hasPosition(Position position) {
        return piecePositions.containsKey(position);
    }

    public PiecePosition getPiecePosition(Side side, Position position) {
        validatePositionExists(position);
        PiecePosition piecePosition = piecePositions.get(position);
        if (!piecePosition.isSameSide(side)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_SAME_SIDE.getMessage());
        }

        return piecePosition;
    }

    private void validatePositionExists(Position position) {
        if (!piecePositions.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }
}
