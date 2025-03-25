package team;

public enum Team {

    HAN,
    CHO,
    ;

    public Team oppsite() {
        if (this.equals(HAN)) {
            return CHO;
        }

        return HAN;
    }
}
