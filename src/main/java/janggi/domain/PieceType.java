package janggi.domain;

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
        if (name.equals(CHA.name)) {
            return CHA;
        }

        if (name.equals(PHO.name)) {
            return PHO;
        }

        if (name.equals(MA.name)) {
            return MA;
        }

        if (name.equals(SANG.name)) {
            return SANG;
        }

        if (name.equals(SA.name)) {
            return SA;
        }

        if (name.equals(BYEONG.name)) {
            return BYEONG;
        }

        if (name.equals(KING.name)) {
            return KING;
        }

        throw new IllegalArgumentException("적절하지 않은 기물 타입입니다.");
    }
}
