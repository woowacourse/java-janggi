package janggi.domain.piece;

import java.util.Map;

public class None extends Piece {
    public None() {
        super("ㅇ", null, TeamType.NONE);
    }

    @Override
    public Piece move(final Map<Position, Piece> pieces, final Position positionToMove) {
        throw new IllegalArgumentException();
    }

    public static boolean isNone(Piece piece) {
        return piece instanceof None;
    }

    public static boolean isNotNone(Piece piece) {
        return !isNone(piece);
    }
}
