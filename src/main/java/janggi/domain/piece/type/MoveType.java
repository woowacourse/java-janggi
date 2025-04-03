package janggi.domain.piece.type;

public enum MoveType {
    NORMAL, PALACE;

    public boolean isPalace() {
        return this == PALACE;
    }
}
