package model.board;

import model.move.Move;
import model.position.Position;

import java.util.List;

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
        for(PalaceRoute route : routes()){
            if(route.matches(move)){
                return true;
            }
        }
        return false;
    }

    private List<PalaceRoute> routes(){
        return List.of(
                new PalaceRoute(Move.of(topLeft(), center())),
                new PalaceRoute(Move.of(center(), bottomRight())),
                new PalaceRoute(Move.of(topRight(), center())),
                new PalaceRoute(Move.of(center(), bottomLeft())),
                new PalaceRoute(Move.of(topLeft(), bottomRight())),
                new PalaceRoute(Move.of(topRight(), bottomLeft()))
        );
    }

    private Position center(){
        if(country==Country.CHO){
            return Position.of(9, 5);
        }
        return Position.of(2, 5);
    }

    private Position topLeft(){
        if(country==Country.CHO){
            return Position.of(8, 4);
        }
        return Position.of(1, 4);
    }

    private Position topRight(){
        if(country ==Country.CHO){
            return Position.of(8, 6);
        }
        return Position.of(1, 6);
    }

    private Position bottomLeft() {
        if (country == Country.CHO) {
            return Position.of(10, 4);
        }
        return Position.of(3, 4);
    }

    private Position bottomRight() {
        if (country == Country.CHO) {
            return Position.of(10, 6);
        }
        return Position.of(3, 6);
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
