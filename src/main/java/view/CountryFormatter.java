package view;

import domain.board.Country;
import java.util.Arrays;

public enum CountryFormatter {
    HAN(Country.HAN, "한나라"),
    CHO(Country.CHO, "초나라"),
    ;

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
                .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 진영입니다."));
    }

    public String getName() {
        return name;
    }
}
