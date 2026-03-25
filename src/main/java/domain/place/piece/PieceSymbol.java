package domain.place.piece;

public enum PieceSymbol {
    GENERAL("궁"),
    CHARIOT("차"),
    CANNON("포"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    SOLDIER("졸");

    private final String display;

    PieceSymbol(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}