package domain.piece;

public enum Team {
    CHO,
    HAN;

    public Team enemy() {
        return values()[1 - ordinal()];
    }
}
