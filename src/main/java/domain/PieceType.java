package domain;

public enum PieceType {
    MA("마"),
    CHA("차"),
    SANG("상"),
    SA("사"),
    GUNG("궁"),
    PHO("포"),
    BYEONG("병"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
