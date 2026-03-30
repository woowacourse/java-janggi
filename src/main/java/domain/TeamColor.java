package domain;

public enum TeamColor {
    CHO,
    HAN;

    public String displayName() {
        if (this == CHO) {
            return CHO;
        }
        return HAN;
    }
}
