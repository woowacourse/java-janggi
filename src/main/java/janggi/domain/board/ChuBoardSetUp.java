package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import java.util.HashMap;
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

    private static final Map<Point, Piece> PIECE_INITIAL_POSITIONS = new HashMap<>() {
        {
            put(new Point(10, 1), new Chariot(Dynasty.CHU));
            put(new Point(10, 4), new Guard(Dynasty.CHU));
            put(new Point(10, 6), new Guard(Dynasty.CHU));
            put(new Point(10, 9), new Chariot(Dynasty.CHU));
            put(new Point(9, 5), new General(Dynasty.CHU));
            put(new Point(8, 2), new Cannon(Dynasty.CHU));
            put(new Point(8, 8), new Cannon(Dynasty.CHU));
            put(new Point(7, 1), new ChuSoldier());
            put(new Point(7, 3), new ChuSoldier());
            put(new Point(7, 5), new ChuSoldier());
            put(new Point(7, 7), new ChuSoldier());
            put(new Point(7, 9), new ChuSoldier());
        }
    };

    private final Map<Point, Piece> piecePositions;

    ChuBoardSetUp(Map<Point, Piece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public Map<Point, Piece> pieces() {
        HashMap<Point, Piece> pieces = new HashMap<>(PIECE_INITIAL_POSITIONS);
        pieces.putAll(piecePositions);
        return pieces;
    }
}
