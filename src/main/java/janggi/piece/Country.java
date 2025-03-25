package janggi.piece;

public enum Country {
    HAN, CHO;

    public Country toggleCountry() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public static Country getFirstTurnCountry(){
        return CHO;
    }
}
