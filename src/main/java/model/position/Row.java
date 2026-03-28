package model.position;

import model.board.Country;
import model.move.Direction;

public record Row(int value) {
    private static final int HAN_EDGE = 1;
    private static final int CHO_EDGE = 10;
    private static final int HAN_SOLDIER = 4;
    private static final int CHO_SOLDIER = 7;
    private static final int HAN_GENERAL = 2;
    private static final int CHO_GENERAL = 9;
    private static final int HAN_CANNON = 3;
    private static final int CHO_CANNON = 8;

    public static Row from(int value) {
        return new Row(value);
    }

    public static int soldier(Country country) {
        if (country == Country.CHO) {
            return CHO_SOLDIER;
        }
        return HAN_SOLDIER;
    }

    public static int cannon(Country country) {
        if (country == Country.CHO) {
            return CHO_CANNON;
        }
        return HAN_CANNON;
    }

    public static int general(Country country) {
        if (country == Country.CHO) {
            return CHO_GENERAL;
        }
        return HAN_GENERAL;
    }

    public static int edgePiece(Country country) {
        if (country == Country.CHO) {
            return CHO_EDGE;
        }
        return HAN_EDGE;
    }

    public int diff(Row to) {
        return to.value - this.value;
    }
}
