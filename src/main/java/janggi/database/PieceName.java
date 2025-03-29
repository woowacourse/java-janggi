package janggi.database;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Country;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceName {
    CANNON(Cannon.class, Cannon::new),
    CHARIOT(Chariot.class, Chariot::new),
    ELEPHANT(Elephant.class, Elephant::new),
    GENERAL(General.class, General::new),
    GUARD(Guard.class, Guard::new),
    HORSE(Horse.class, Horse::new),
    SOLDIER(Soldier.class, Soldier::new);

    private final Class<? extends Piece> type;
    private final Function<Country, ? extends Piece> construct;

    PieceName(final Class<? extends Piece> type, final Function<Country, ? extends Piece> construct) {
        this.type = type;
        this.construct = construct;
    }

    public static String convertPieceName(final Piece piece){
        return Arrays.stream(values())
                .filter(pieceName -> pieceName.type.equals(piece.getClass()))
                .map(PieceName::name)
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    public static Piece convertPiece(final String piece, final Country country){
        return Arrays.stream(values())
                .filter(pieceName -> piece.equals(pieceName.name()))
                .map(pieceName -> pieceName.construct.apply(country))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
