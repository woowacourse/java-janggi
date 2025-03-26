package janggi.piece;

public enum Team {
    CHO,
    HAN,
    BLANK;

    public static Team next(Team currentTeam) {
        if (currentTeam == CHO) {
            return HAN;
        }
        if (currentTeam == HAN) {
            return CHO;
        }
        return BLANK;
    }
}
