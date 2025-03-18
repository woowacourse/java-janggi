package janggi.domain;

import janggi.domain.piece.Elephant;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Piece;
import java.util.Map;

public enum HanBoardSetUp implements BoardSetUp {
    INNER_ELEPHAN(Map.of(
            new Point(1, 2), new Knight(Dynasty.HAN),
            new Point(1, 3), new Elephant(Dynasty.HAN),
            new Point(1, 7), new Elephant(Dynasty.HAN),
            new Point(1, 8), new Knight(Dynasty.HAN)
    )),
    OUTER_ELEPHAN(Map.of(
            new Point(1, 2), new Elephant(Dynasty.HAN),
            new Point(1, 3), new Knight(Dynasty.HAN),
            new Point(1, 7), new Knight(Dynasty.HAN),
            new Point(1, 8), new Elephant(Dynasty.HAN)
    )),
    RIGHT_ELEPHAN(Map.of(
            new Point(1, 2), new Knight(Dynasty.HAN),
            new Point(1, 3), new Elephant(Dynasty.HAN),
            new Point(1, 7), new Knight(Dynasty.HAN),
            new Point(1, 8), new Elephant(Dynasty.HAN)
    )),
    LEFT_ELEPHAN(Map.of(
            new Point(1, 2), new Elephant(Dynasty.HAN),
            new Point(1, 3), new Knight(Dynasty.HAN),
            new Point(1, 7), new Elephant(Dynasty.HAN),
            new Point(1, 8), new Knight(Dynasty.HAN)
    ));

    private final Map<Point, Piece> piecePositions;

    HanBoardSetUp(Map<Point, Piece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public Map<Point, Piece> getPiecePositions() {
        return piecePositions;
    }
}
