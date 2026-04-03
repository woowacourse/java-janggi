package domain.country;

import java.util.Arrays;

public enum CountryType {
    HAN("han"),
    CHO("cho"),
    ;

    private static final String NOT_FOUND_COUNTRY_TYPE = "[ERROR] 존재하지 않는 나라입니다.";

    private final String name;

    CountryType(String name) {
        this.name = name;
    }

    public CountryType from(String name) {
        return Arrays.stream(CountryType.values())
                .filter(countryType -> countryType.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_COUNTRY_TYPE));
    }

    public CountryType anotherCountryType() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public String getName() {
        return name;
    }
}
