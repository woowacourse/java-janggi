package janggi.factory;

import janggi.domain.Position;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.Piece;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.PieceState;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;

public enum PieceFactory {
    GENERAL1(Side.CHO, 9, 5, new General()),
    GUARD1(Side.CHO, 10, 4, new Guard()),
    GUARD2(Side.CHO, 10, 6, new Guard()),
    ELEPHANT1(Side.CHO, 10, 2, new Elephant()),
    ELEPHANT2(Side.CHO, 10, 7, new Elephant()),
    HORSE1(Side.CHO, 10, 3, new Horse()),
    HORSE2(Side.CHO, 10, 8, new Horse()),
    CHARIOT1(Side.CHO, 10, 1, new Chariot()),
    CHARIOT2(Side.CHO, 10, 9, new Chariot()),
    CANNON1(Side.CHO, 8, 2, new Cannon()),
    CANNON2(Side.CHO, 8, 8, new Cannon()),
    SOLDIER1(Side.CHO, 7, 1, new Soldier()),
    SOLDIER2(Side.CHO, 7, 3, new Soldier()),
    SOLDIER3(Side.CHO, 7, 5, new Soldier()),
    SOLDIER4(Side.CHO, 7, 7, new Soldier()),
    SOLDIER5(Side.CHO, 7, 9, new Soldier()),

    GENERAL2(Side.HAN, 2, 5, new General()),
    GUARD3(Side.HAN, 1, 4, new Guard()),
    GUARD4(Side.HAN, 1, 6, new Guard()),
    ELEPHANT3(Side.HAN, 1, 2, new Elephant()),
    ELEPHANT4(Side.HAN, 1, 7, new Elephant()),
    HORSE3(Side.HAN, 1, 3, new Horse()),
    HORSE4(Side.HAN, 1, 8, new Horse()),
    CHARIOT3(Side.HAN, 1, 1, new Chariot()),
    CHARIOT4(Side.HAN, 1, 9, new Chariot()),
    CANNON3(Side.HAN, 3, 2, new Cannon()),
    CANNON4(Side.HAN, 3, 8, new Cannon()),
    SOLDIER6(Side.HAN, 4, 1, new Soldier()),
    SOLDIER7(Side.HAN, 4, 3, new Soldier()),
    SOLDIER8(Side.HAN, 4, 5, new Soldier()),
    SOLDIER9(Side.HAN, 4, 7, new Soldier()),
    SOLDIER10(Side.HAN, 4, 9, new Soldier()),
    ;

    private final Side side;
    private final int row;
    private final int column;
    private final PieceBehavior pieceBehavior;

    PieceFactory(Side side, int row, int column, PieceBehavior pieceBehavior) {
        this.side = side;
        this.row = row;
        this.column = column;
        this.pieceBehavior = pieceBehavior;
    }

    public static Map<Position, PieceState> initialize() {
        Map<Position, PieceState> map = new HashMap<>();

        for (PieceFactory value : PieceFactory.values()) {
            Position position = Position.of(value.row, value.column);
            Piece piece = new Piece(value.side, value.pieceBehavior);
            PieceState pieceState = new PieceState(position, piece);
            map.put(position, pieceState);
        }

        return map;
    }

    public PieceBehavior getPieceBehavior() {
        return pieceBehavior;
    }
}
