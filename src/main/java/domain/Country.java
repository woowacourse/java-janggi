package domain;

import domain.piece.Piece;

public enum Country {
    HAN("한나라"),
    CHO("초나라"),
    ;

    private final String name;

    Country(String name) {
        this.name = name;
    }

    public static boolean isSameContry(Piece curr, Piece target) {
        return curr.getCountry() == target.getCountry();
    }

    public String getName() {
        return name;
    }
}
