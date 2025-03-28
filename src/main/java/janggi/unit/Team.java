package janggi.unit;

public enum Team {
    HAN,
    CHO,
    ;

    public Team getOppositie() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
