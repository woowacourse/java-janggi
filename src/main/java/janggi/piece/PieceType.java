package janggi.piece;

import java.util.Arrays;

public enum PieceType {

    SOLDIER("병", new Score(2)),
    CHARIOT("차", new Score(13)),
    KING("왕", new Score(0)),
    PAWN("졸", new Score(2)),
    HORSE("마", new Score(5)),
    CANNON("포", new Score(7)),
    GUARD("사", new Score(3)),
    ELEPHANT("상", new Score(3)),
    ;

    private final String name;
    private final Score score;

    PieceType(final String name, final Score score) {
        this.name = name;
        this.score = score;
    }

    public static PieceType from(final String value) {
        return Arrays.stream(values())
                .filter(type -> type.name.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 일치하는 타입이 존재하지 않습니다."));
    }

    public static boolean isKing(final PieceType pieceType) {
        return KING.equals(pieceType);
    }

    public static boolean isCannon(final PieceType pieceType) {
        return CANNON.equals(pieceType);
    }

    public String getValue() {
        return name;
    }

    public Score getScore() {
        return score;
    }
}
