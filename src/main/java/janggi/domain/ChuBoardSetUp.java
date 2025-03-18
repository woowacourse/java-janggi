package janggi.domain;

import janggi.domain.piece.Elephant;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Piece;
import java.util.Map;

public enum ChuBoardSetUp implements BoardSetUp {
    INNER_ELEPHAN(Map.of(
            new Point(10, 2), new Knight(Dynasty.CHU),
            new Point(10, 3), new Elephant(Dynasty.CHU),
            new Point(10, 7), new Elephant(Dynasty.CHU),
            new Point(10, 8), new Knight(Dynasty.CHU)
    )),
    OUTER_ELEPHAN(Map.of(
            new Point(10, 2), new Elephant(Dynasty.CHU),
            new Point(10, 3), new Knight(Dynasty.CHU),
            new Point(10, 7), new Knight(Dynasty.CHU),
            new Point(10, 8), new Elephant(Dynasty.CHU)
    )),
    RIGHT_ELEPHAN(Map.of(
            new Point(10, 2), new Knight(Dynasty.CHU),
            new Point(10, 3), new Elephant(Dynasty.CHU),
            new Point(10, 7), new Knight(Dynasty.CHU),
            new Point(10, 8), new Elephant(Dynasty.CHU)
    )),
    LEFT_ELEPHAN(Map.of(
            new Point(10, 2), new Elephant(Dynasty.CHU),
            new Point(10, 3), new Knight(Dynasty.CHU),
            new Point(10, 7), new Elephant(Dynasty.CHU),
            new Point(10, 8), new Knight(Dynasty.CHU)
    ));

    private final Map<Point, Piece> piecePositions;

    ChuBoardSetUp(Map<Point, Piece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public Map<Point, Piece> getPiecePositions() {
        return piecePositions;
    }
}
