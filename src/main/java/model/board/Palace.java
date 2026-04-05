package model.board;

import model.position.Position;

public class Palace {
    private static final int MIN_COLUMN = 4;
    private static final int MAX_COLUMN = 6;
    private static final int HAN_MIN_ROW = 1;
    private static final int HAN_MAX_ROW = 3;
    private static final int CHO_MIN_ROW = 8;
    private static final int CHO_MAX_ROW = 10;

    private final Country country;

    private Palace(Country country) {
        this.country = country;
    }

    public static Palace from(Country country) {
        return new Palace(country);
    }

    public boolean contains(Position position) {
        if (!isInsideColumn(position)) {
            return false;
        }
        return isInsideRow(position);
    }

    private boolean isInsideColumn(Position position) {
        int column = position.column().value();
        return column >= MIN_COLUMN && column <= MAX_COLUMN;
    }

    private boolean isInsideRow(Position position) {
        if (country == Country.CHO) {
            return isChoRow(position);
        }
        return isHanRow(position);
    }

    private boolean isChoRow(Position position) {
        int row = position.row().value();
        return row >= CHO_MIN_ROW && row <= CHO_MAX_ROW;
    }

    private boolean isHanRow(Position position) {
        int row = position.row().value();
        return row >= HAN_MIN_ROW && row <= HAN_MAX_ROW;
    }
}
