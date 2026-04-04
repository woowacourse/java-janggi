package janggi.domain.space.piece;

import java.util.Arrays;

public enum PieceType {

    CHA("차", 13),
    PHO("포", 7),
    MA("마", 5),
    SANG("상", 3),
    SA("사", 3),
    BYEONG("병", 2),
    KING("궁", 0),
    ;

    private final String name;
    private final int score;

    PieceType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public static PieceType from(String name) {
        return Arrays.stream(values())
                .filter(piece -> piece.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("적절하지 않은 기물 타입입니다."));
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
