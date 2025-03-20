package domain.piece;

public enum Country {
    CHO("초"),
    HAN("한");

    private final String countryName;

    Country(String countryName) {
        this.countryName = countryName;
    }

    public static Country convertTurn(Country country){
        if(country == Country.HAN){
            return Country.CHO;
        }
        return Country.HAN;
    }

    public String getCountryName() {
        return countryName;
    }
}
