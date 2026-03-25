package domain;

public enum PieceType {
    // 차, 마, 상, 사, 장, 포, 졸
    CHA("차"),
    MA("마"),
    SANG("상"),
    SA("사"),
    JANG("장"),
    PO("포"),
    JOL("졸");

    private String name;

    PieceType(String name) {
        this.name = name;
    }
}
