package janggi.domain;

public enum Side {
    CHO,
    HAN,
    ;

    public boolean isAlly(Side other) {
        return this.equals(other);
    }
}
