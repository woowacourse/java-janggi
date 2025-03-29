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

    public static Team convert(String teamName) {
        if(teamName.equals("HAN"))return HAN;
        if(teamName.equals("CHO"))return CHO;
        return BLANK;
    }
}
