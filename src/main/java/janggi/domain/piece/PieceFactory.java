package janggi.domain.piece;

import java.util.Map;
import java.util.function.Function;

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

    private static final Map<PieceType, Function<Side, Piece>> CREATORS = Map.of(
            PieceType.ADVISOR, Advisor::new,
            PieceType.CANNON, Cannon::new,
            PieceType.CHARIOT, Chariot::new,
            PieceType.ELEPHANT, Elephant::new,
            PieceType.GENERAL, General::new,
            PieceType.HORSE, Horse::new,
            PieceType.SOLDIER, Soldier::new,
            PieceType.NONE, side -> Empty.INSTANCE
    );

    public static Piece create(PieceType type, Side side) {
        return CREATORS.get(type).apply(side);
    }

}
