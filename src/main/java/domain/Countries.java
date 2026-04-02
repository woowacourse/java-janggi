package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Countries {
    private static final String NOT_FOUND_COUNTRY_BY_COUNTRY_TYPE = "[ERROR] 해당 진영을 찾을 수 없습니다.";

    private final List<Country> countries = new ArrayList<>();

    public Countries() {
        countries.add(new Country(CountryType.CHO, 72d));
        countries.add(new Country(CountryType.HAN, 73.5d));
    }

    public Country findCountryByCountryType(CountryType countryType) {
        return countries.stream()
                .filter(country -> country.getCountryType().equals(countryType))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(NOT_FOUND_COUNTRY_BY_COUNTRY_TYPE));
    }

    public Map<CountryType, Double> getScores() {
        Map<CountryType, Double> scores = new HashMap<>();
        scores.put(CountryType.CHO, findCountryByCountryType(CountryType.CHO).getScore());
        scores.put(CountryType.HAN, findCountryByCountryType(CountryType.HAN).getScore());
        return scores;
    }
}
