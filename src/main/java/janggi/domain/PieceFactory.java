package janggi.domain;

import janggi.domain.movement.Position;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;

public enum PieceFactory {

    GENERAL1(9, 5, new General(Side.CHO)),
    GUARD1(10, 4, new Guard(Side.CHO)),
    GUARD2(10, 6, new Guard(Side.CHO)),
    ELEPHANT1(10, 2, new Elephant(Side.CHO)),
    ELEPHANT2(10, 7, new Elephant(Side.CHO)),
    HORSE1(10, 3, new Horse(Side.CHO)),
    HORSE2(10, 8, new Horse(Side.CHO)),
    CHARIOT1(10, 1, new Chariot(Side.CHO)),
    CHARIOT2(10, 9, new Chariot(Side.CHO)),
    CANNON1(8, 2, new Cannon(Side.CHO)),
    CANNON2(8, 8, new Cannon(Side.CHO)),
    SOLDIER1(7, 1, new Soldier(Side.CHO)),
    SOLDIER2(7, 3, new Soldier(Side.CHO)),
    SOLDIER3(7, 5, new Soldier(Side.CHO)),
    SOLDIER4(7, 7, new Soldier(Side.CHO)),
    SOLDIER5(7, 9, new Soldier(Side.CHO)),
    GENERAL2(2, 5, new General(Side.HAN)),
    GUARD3(1, 4, new Guard(Side.HAN)),
    GUARD4(1, 6, new Guard(Side.HAN)),
    ELEPHANT3(1, 2, new Elephant(Side.HAN)),
    ELEPHANT4(1, 7, new Elephant(Side.HAN)),
    HORSE3(1, 3, new Horse(Side.HAN)),
    HORSE4(1, 8, new Horse(Side.HAN)),
    CHARIOT3(1, 1, new Chariot(Side.HAN)),
    CHARIOT4(1, 9, new Chariot(Side.HAN)),
    CANNON3(3, 2, new Cannon(Side.HAN)),
    CANNON4(3, 8, new Cannon(Side.HAN)),
    SOLDIER6(4, 1, new Soldier(Side.HAN)),
    SOLDIER7(4, 3, new Soldier(Side.HAN)),
    SOLDIER8(4, 5, new Soldier(Side.HAN)),
    SOLDIER9(4, 7, new Soldier(Side.HAN)),
    SOLDIER10(4, 9, new Soldier(Side.HAN)),
    ;

    private final int row;
    private final int column;
    private final Piece piece;

    PieceFactory(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }

    public static Map<Position, Piece> initialize() {
        Map<Position, Piece> startingPieces = new HashMap<>();

        for (PieceFactory value : PieceFactory.values()) {
            startingPieces.put(Position.of(value.row, value.column), value.piece);
        }

        return startingPieces;
    }
}
