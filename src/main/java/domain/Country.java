package domain;

import domain.piece.Piece;

import java.util.Arrays;

public enum Country {
    HAN("한나라"),
    CHO("초나라"),
    ;

    private final String name;

    Country(String name) {
        this.name = name;
    }

    public static boolean isSameCountry(Piece curr, Piece target) {
        return curr.getCountry() == target.getCountry();
    }

    public static Country fromName(String name) {
        return Arrays.stream(Country.values())
                .filter(country -> country.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 국가 이름: " + name));
    }

    public String getName() {
        return name;
    }
}
