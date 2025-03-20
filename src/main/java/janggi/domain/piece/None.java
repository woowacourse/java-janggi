package janggi.domain.piece;

import java.util.Map;

public class None extends Piece {
    public None() {
        super("ㅁ", null, Team.NONE);
    }

    public static boolean checkIsNone(Piece piece) {
        return piece instanceof None;
    }

    public static boolean isNotNone(Piece piece) {
        return !checkIsNone(piece);
    }

    @Override
    public Piece move(final Map<Position, Piece> pieces, final Position positionToMove) {
        throw new IllegalArgumentException("빈칸은 움직일 수 없습니다.");
    }
}
