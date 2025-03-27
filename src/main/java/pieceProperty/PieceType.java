package pieceProperty;

public enum PieceType {
    BYEONG,
    CHA,
    JANGGUN,
    JOL,
    MA,
    PO,
    SA,
    SANG;

    PieceType() {
    }

    public boolean isPo() {
        return this.equals(PO);
    }

    public boolean isJanggun() {
        return this.equals(JANGGUN);
    }
}
