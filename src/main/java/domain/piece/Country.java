package domain.piece;

public enum Country {

    CHO("초"),
    HAN("한");

    private final String countryName;

    Country(String countryName) {
        this.countryName = countryName;
    }

    public Country convertCountry() {
        if (isHan()) {
            return Country.CHO;
        }
        return Country.HAN;
    }

    public String getCountryName() {
        return countryName;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public boolean isHan() {
        return this == HAN;
    }
}
