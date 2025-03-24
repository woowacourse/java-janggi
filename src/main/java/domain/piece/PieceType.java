package domain.piece;

public enum PieceType {
    CANNON("포"),
    CHARIOT("차"),
    ELEPHANT("상"),
    GENERAL("왕"),
    GUARD("사"),
    HORSE("마"),
    ZZU("쭈");

    private final String title;

    PieceType(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
