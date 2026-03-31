package view;

import domain.Country;
import java.util.Arrays;

public enum CountryFormatter {
    HAN(Country.HAN, "한나라"),
    CHO(Country.CHO, "초나라"),
    ;

    private static final String NOT_FOUND_COUNTRY = "[ERROR] 존재하지 않는 진영입니다.";

    private final Country country;
    private final String name;

    CountryFormatter(Country country, String name) {
        this.country = country;
        this.name = name;
    }

    public static String from(Country country) {
        return Arrays.stream(CountryFormatter.values())
                .filter(countryFormatter -> countryFormatter.country == country)
                .map(CountryFormatter::getName)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(NOT_FOUND_COUNTRY));
    }

    public String getName() {
        return name;
    }
}
