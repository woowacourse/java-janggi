package janggi.domain.side;

public enum Side {
    HAN,
    CHO,
    NONE,
    ;

    public static boolean isSameSide(Side firstSide, Side secondSide){
        return firstSide.equals(secondSide);
    }
}
