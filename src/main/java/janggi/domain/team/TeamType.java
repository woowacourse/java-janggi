package janggi.domain.team;

public enum TeamType {
    CHU,
    HAN,
    ;

    public TeamType findOpponent() {
        if (this == CHU) {
            return HAN;
        }
        return CHU;
    }
}
