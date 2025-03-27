package janggi.temp;

public enum Team {

    CHO, HAN;

    public Team opposite() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
