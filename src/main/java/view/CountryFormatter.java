package view;

import domain.CountryType;
import java.util.Arrays;

public enum CountryFormatter {
    HAN(CountryType.HAN, "한나라"),
    CHO(CountryType.CHO, "초나라"),
    ;

    private static final String NOT_FOUND_COUNTRY = "[ERROR] 존재하지 않는 진영입니다.";

    private final CountryType countryType;
    private final String name;

    CountryFormatter(CountryType countryType, String name) {
        this.countryType = countryType;
        this.name = name;
    }

    public static String from(CountryType countryType) {
        return Arrays.stream(CountryFormatter.values())
                .filter(countryFormatter -> countryFormatter.countryType == countryType)
                .map(CountryFormatter::getName)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(NOT_FOUND_COUNTRY));
    }

    public String getName() {
        return name;
    }
}
