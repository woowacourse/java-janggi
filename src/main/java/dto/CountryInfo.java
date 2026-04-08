package dto;

import domain.board.Country;

public record CountryInfo(
        Country country,
        Country otherSide
) {
    public static CountryInfo of(Country country, Country otherSide) {
        return new CountryInfo(country, otherSide);
    }
}
