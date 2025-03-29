package janggi.piece;

public enum MoveType {
    NORMAL, PALACE;

    public boolean isPalace() {
        return this == PALACE;
    }
}
