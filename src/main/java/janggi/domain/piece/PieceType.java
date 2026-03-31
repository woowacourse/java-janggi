package janggi.domain.piece;

import java.util.Arrays;

public enum PieceType {

    CHA("차"),
    PHO("포"),
    MA("마"),
    SANG("상"),
    SA("사"),
    BYEONG("병"),
    KING("궁"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public static PieceType from(String name) {
        return Arrays.stream(values())
                .filter(piece -> piece.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("적절하지 않은 기물 타입입니다."));
    }
}
