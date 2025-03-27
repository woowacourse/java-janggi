package domain.piece;

public enum Team {
    HAN,
    CHO,
    DEFAULT;

    public static Team getStartingTeam() {
        return CHO;
    }

    public Team opposite() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
