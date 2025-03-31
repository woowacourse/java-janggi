package piece;

public enum Country {
    CHO,
    HAN;

    public static Country of(final String country) {
        return valueOf(country);
    }

    public Country reverseCountry() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
