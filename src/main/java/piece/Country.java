package piece;

public enum Country {
    CHO,
    HAN;

    public Country reverseCountry() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
