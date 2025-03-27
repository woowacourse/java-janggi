package domain;

public enum Team {

    CHO,
    HAN;

    public Team getEnemy() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
