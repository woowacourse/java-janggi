package janggi.domain.piece;

import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.Cannon;
import janggi.domain.piece.unit.Chariot;
import janggi.domain.piece.unit.Elephant;
import janggi.domain.piece.unit.Empty;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Horse;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;

public class PieceFactory {
    public static Piece create(PieceType pieceType, Side side) {
        return switch (pieceType) {
            case ADVISOR -> new Advisor(side);
            case CANNON -> new Cannon(side);
            case CHARIOT -> new Chariot(side);
            case ELEPHANT -> new Elephant(side);
            case GENERAL -> new General(side);
            case HORSE -> new Horse(side);
            case SOLDIER -> new Soldier(side);
            case NONE -> Empty.INSTANCE;
        };
    }
}
