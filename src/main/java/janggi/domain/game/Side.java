package janggi.domain.game;

public enum Side {
    CHO,
    HAN,
    ;

    public Side opposite() {
        if (this.equals(CHO)) {
            return HAN;
        }
        return CHO;
    }
}
