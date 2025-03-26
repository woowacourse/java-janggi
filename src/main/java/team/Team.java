package team;

public enum Team {

    HAN,
    CHO,
    ;

    public static Team getFirstTurnTeam() {
        return CHO;
    }

    public Team oppsite() {
        if (this.equals(HAN)) {
            return CHO;
        }

        return HAN;
    }
}
