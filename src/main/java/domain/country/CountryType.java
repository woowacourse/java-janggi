package domain.country;

public enum CountryType {
    HAN,
    CHO,
    ;

    public CountryType anotherCountryType() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
