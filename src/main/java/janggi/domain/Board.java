package janggi.domain;

import janggi.domain.piece.PiecePosition;
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
}
