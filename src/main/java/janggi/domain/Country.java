package janggi.domain;

public enum Country {
    HAN,
    CHO,
    ;

    public Country opponent() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
