package domain;

public enum Country {
    HAN,
    CHO,
    ;

    public static Country anotherCountry(Country country) {
        if (country == HAN) {
            return CHO;
        }
        return HAN;
    }
}
