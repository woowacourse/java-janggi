package janggi.domain.board;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanSoldier;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Point;
import java.util.HashMap;
import java.util.Map;

public enum HanBoardSetUp implements BoardSetUp {
    INNER_ELEPHANT(Map.of(
            new Point(1, 2), new Horse(Dynasty.HAN),
            new Point(1, 3), new Elephant(Dynasty.HAN),
            new Point(1, 7), new Elephant(Dynasty.HAN),
            new Point(1, 8), new Horse(Dynasty.HAN)
    )),
    OUTER_ELEPHANT(Map.of(
            new Point(1, 2), new Elephant(Dynasty.HAN),
            new Point(1, 3), new Horse(Dynasty.HAN),
            new Point(1, 7), new Horse(Dynasty.HAN),
            new Point(1, 8), new Elephant(Dynasty.HAN)
    )),
    RIGHT_ELEPHANT(Map.of(
            new Point(1, 2), new Horse(Dynasty.HAN),
            new Point(1, 3), new Elephant(Dynasty.HAN),
            new Point(1, 7), new Horse(Dynasty.HAN),
            new Point(1, 8), new Elephant(Dynasty.HAN)
    )),
    LEFT_ELEPHANT(Map.of(
            new Point(1, 2), new Elephant(Dynasty.HAN),
            new Point(1, 3), new Horse(Dynasty.HAN),
            new Point(1, 7), new Elephant(Dynasty.HAN),
            new Point(1, 8), new Horse(Dynasty.HAN)
    ));

    private static final Map<Point, Piece> PIECE_INITIAL_POSITIONS = new HashMap<>() {
        {
            put(new Point(1, 1), new Chariot(Dynasty.HAN));
            put(new Point(1, 4), new Guard(Dynasty.HAN));
            put(new Point(1, 6), new Guard(Dynasty.HAN));
            put(new Point(1, 9), new Chariot(Dynasty.HAN));
            put(new Point(2, 5), new General(Dynasty.HAN));
            put(new Point(3, 2), new Cannon(Dynasty.HAN));
            put(new Point(3, 8), new Cannon(Dynasty.HAN));
            put(new Point(4, 1), new HanSoldier());
            put(new Point(4, 3), new HanSoldier());
            put(new Point(4, 5), new HanSoldier());
            put(new Point(4, 7), new HanSoldier());
            put(new Point(4, 9), new HanSoldier());
        }
    };

    private final Map<Point, Piece> piecePositions;

    HanBoardSetUp(Map<Point, Piece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public Map<Point, Piece> pieces() {
        HashMap<Point, Piece> pieces = new HashMap<>(PIECE_INITIAL_POSITIONS);
        pieces.putAll(piecePositions);
        return pieces;
    }
}
