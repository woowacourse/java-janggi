package janggi.domain.piece;

public class None extends Piece {

    public None() {
        super("ㅇ", null, TeamType.NONE);
    }

    public static boolean isNone(Piece piece) {
        return piece instanceof None;
    }

    public static boolean isNotNone(Piece piece) {
        return !isNone(piece);
    }
}
