package model.board;

import java.util.List;
import java.util.function.UnaryOperator;
import model.move.Direction;

public enum Country {
    HAN("한", List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT), "\u001B[31m", String::toLowerCase),
    CHO("초", List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT), "\u001B[32m", String::toUpperCase);

    public static final String RESET = "\u001B[0m";
    private final String title;
    private final List<Direction> forbidden;
    private final String color;
    private final UnaryOperator<String> converter;

    Country(String title, List<Direction> forbidden, String color, UnaryOperator<String> converter) {
        this.title = title;
        this.forbidden = forbidden;
        this.color = color;
        this.converter = converter;
    }

    public static Country fromCountry(String name) {
        for (Country country : Country.values()) {
            if (country.name().equals(name)) {
                return country;
            }
        }
        throw new IllegalArgumentException("[ERROR] 없는 나라입니다.");
    }

    public Country convertCountry() {
        if (this == Country.CHO) {
            return Country.HAN;
        }
        return Country.CHO;
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

    public String convertLabel(String label) {
        return converter.apply(label);
    }
}
