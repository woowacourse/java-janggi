package domain;

public enum PieceType {

    WANG("왕"),
    SA("사"),
    CHA("차"),
    SANG("상"),
    MA("마"),
    PO("포"),
    BYEONG("병"),
    ;

    private final String title;

    PieceType(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
