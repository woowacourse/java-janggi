package janggi.domain.side;

public enum Side {
    HAN,
    CHO,
    NONE,
    ;
    public static boolean isSameSide(Side firstSide, Side secondSide){
        if(NONE.equals(firstSide) || NONE.equals(secondSide)){
            throw new IllegalStateException("NONE Side 상태입니다.");
        }
        return firstSide.equals(secondSide);
    }
    
}
