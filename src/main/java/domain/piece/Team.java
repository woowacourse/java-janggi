package domain.piece;

public enum Team {
    CHO, HAN;

    public boolean isCho() {
        return this == CHO;
    }

    public Team changeTeam() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public boolean isHan() {
        return this == HAN;
    }
}
