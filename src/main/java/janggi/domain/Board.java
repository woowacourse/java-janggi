package janggi.domain;

import janggi.domain.piece.PiecePosition;
import java.util.Collections;
import java.util.Map;

public class Board {

    private final Map<Position, PiecePosition> piecePositions;

    public Board(Map<Position, PiecePosition> piecePositions) {
        this.piecePositions = piecePositions;
    }

    public Map<Position, PiecePosition> getPiecePositions() {
        return Collections.unmodifiableMap(piecePositions);
    }
}
