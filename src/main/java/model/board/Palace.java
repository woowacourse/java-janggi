package model.board;

import java.util.List;

import model.move.Move;
import model.position.Position;

public class Palace {
    private static final int LEFT_COLUMN = 4;
    private static final int CENTER_COLUMN = 5;
    private static final int RIGHT_COLUMN = 6;

    private static final int HAN_TOP_ROW = 1;
    private static final int HAN_CENTER_ROW = 2;
    private static final int HAN_BOTTOM_ROW = 3;

    private static final int CHO_TOP_ROW = 8;
    private static final int CHO_CENTER_ROW = 9;
    private static final int CHO_BOTTOM_ROW = 10;

    private final Country country;
    private final List<PalaceRoute> routes;

    private Palace(Country country) {
        this.country = country;
        this.routes = createRoutes();
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

    public boolean isDiagonalMove(Move move) {
        if (!contains(move.from())) {
            return false;
        }
        if (!contains(move.to())) {
            return false;
        }
        return matchesRoute(move);
    }

    private boolean matchesRoute(Move move) {
        for (PalaceRoute route : routes) {
            if (route.matches(move)) {
                return true;
            }
        }
        return false;
    }

    private List<PalaceRoute> createRoutes() {
        return List.of(
                new PalaceRoute(Move.of(topLeft(), center())),
                new PalaceRoute(Move.of(center(), bottomRight())),
                new PalaceRoute(Move.of(topRight(), center())),
                new PalaceRoute(Move.of(center(), bottomLeft())),
                new PalaceRoute(Move.of(topLeft(), bottomRight())),
                new PalaceRoute(Move.of(topRight(), bottomLeft()))
        );
    }

    private Position center() {
        return Position.of(centerRow(), CENTER_COLUMN);
    }

    private Position topLeft() {
        return Position.of(topRow(), LEFT_COLUMN);
    }

    private Position topRight() {
        return Position.of(topRow(), RIGHT_COLUMN);
    }

    private Position bottomLeft() {
        return Position.of(bottomRow(), LEFT_COLUMN);
    }

    private Position bottomRight() {
        return Position.of(bottomRow(), RIGHT_COLUMN);
    }

    private int topRow() {
        if (country == Country.CHO) {
            return CHO_TOP_ROW;
        }
        return HAN_TOP_ROW;
    }

    private int centerRow() {
        if (country == Country.CHO) {
            return CHO_CENTER_ROW;
        }
        return HAN_CENTER_ROW;
    }

    private int bottomRow() {
        if (country == Country.CHO) {
            return CHO_BOTTOM_ROW;
        }
        return HAN_BOTTOM_ROW;
    }

    private boolean isInsideColumn(Position position) {
        int column = position.column().value();
        return column >= LEFT_COLUMN && column <= RIGHT_COLUMN;
    }

    private boolean isInsideRow(Position position) {
        if (country == Country.CHO) {
            return isChoRow(position);
        }
        return isHanRow(position);
    }

    private boolean isChoRow(Position position) {
        int row = position.row().value();
        return row >= CHO_TOP_ROW && row <= CHO_BOTTOM_ROW;
    }

    private boolean isHanRow(Position position) {
        int row = position.row().value();
        return row >= HAN_TOP_ROW && row <= HAN_BOTTOM_ROW;
    }
}