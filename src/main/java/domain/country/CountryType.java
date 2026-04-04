package domain.country;

public enum CountryType {
    HAN(73.5d),
    CHO(72d),
    ;

    private final double initScore;

    CountryType(double initScore) {
        this.initScore = initScore;
    }

    public CountryType anotherCountryType() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public double getInitScore() {
        return initScore;
    }
}
