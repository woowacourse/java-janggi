package model.board;

import java.util.List;
import model.move.Direction;

public enum Country {
    HAN("한", List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT), "\u001B[31m"),
    CHO("초", List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT), "\u001B[32m");

    public static final String RESET = "\u001B[0m";
    private final String title;
    private final List<Direction> forbidden;
    private final String color;

    Country(String title, List<Direction> forbidden, String color) {
        this.title = title;
        this.forbidden = forbidden;
        this.color = color;
    }

    public static Country fromCountry(String name) {
        for (Country country : Country.values()) {
            if (country.name().equals(name)) {
                return country;
            }
        }
        throw new IllegalArgumentException("[ERROR] 없는 나라입니다.");
    }

    public boolean myTurn(Country country) {
        return this == country;
    }

    public String title() {
        return title;
    }

    public List<Direction> forbidden() {
        return forbidden;
    }

    public String color() {
        return color;
    }
}
