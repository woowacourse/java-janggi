package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import java.util.Map;

public enum ChuBoardSetUp implements BoardSetUp {
    INNER_ELEPHANT(Map.of(
            new Point(10, 2), new Horse(Dynasty.CHU),
            new Point(10, 3), new Elephant(Dynasty.CHU),
            new Point(10, 7), new Elephant(Dynasty.CHU),
            new Point(10, 8), new Horse(Dynasty.CHU)
    )),
    OUTER_ELEPHANT(Map.of(
            new Point(10, 2), new Elephant(Dynasty.CHU),
            new Point(10, 3), new Horse(Dynasty.CHU),
            new Point(10, 7), new Horse(Dynasty.CHU),
            new Point(10, 8), new Elephant(Dynasty.CHU)
    )),
    RIGHT_ELEPHANT(Map.of(
            new Point(10, 2), new Horse(Dynasty.CHU),
            new Point(10, 3), new Elephant(Dynasty.CHU),
            new Point(10, 7), new Horse(Dynasty.CHU),
            new Point(10, 8), new Elephant(Dynasty.CHU)
    )),
    LEFT_ELEPHANT(Map.of(
            new Point(10, 2), new Elephant(Dynasty.CHU),
            new Point(10, 3), new Horse(Dynasty.CHU),
            new Point(10, 7), new Elephant(Dynasty.CHU),
            new Point(10, 8), new Horse(Dynasty.CHU)
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
