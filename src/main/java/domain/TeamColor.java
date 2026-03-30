package domain;

public enum TeamColor {
    CHO,
    HAN;

    public String displayName() {
        if (this == CHO) {
            return TeamColor.CHO.name();
        }
        return TeamColor.HAN.name();
    }
}
