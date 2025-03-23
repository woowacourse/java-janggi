package domain;

public enum Team {
    HAN,
    CHO;

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
