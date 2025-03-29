package janggi.piece;

import java.util.Arrays;

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

    public static Country StringToCountry(final String countryText){
        return Arrays.stream(Country.values())
                .filter(country -> countryText.equals(country.name()))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
