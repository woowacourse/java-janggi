package janggi.view;

public enum PieceSymbol {

    CANNON("포"),
    CHARIOT("차"),
    ELEPHANT("상"),
    GENERAL("왕"),
    GUARD("사"),
    HORSE("마"),
    SOLDIER_JOL("졸"),
    SOLDIER_BYEONG("병"),
    EMPTY_SPACE("ㅤ");

    private final String displayName;

    PieceSymbol(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
