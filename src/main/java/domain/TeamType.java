package domain;

public enum TeamType {

    CHO,
    HAN;

    public TeamType otherTeam() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
