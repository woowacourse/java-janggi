package model.position;

import model.board.Country;

public record Row(int value) {
    private static final int HAN_EDGE = 1;
    private static final int CHO_EDGE = 10;
    private static final int HAN_SOLDIER = 4;
    private static final int CHO_SOLDIER = 7;
    private static final int HAN_GENERAL = 2;
    private static final int CHO_GENERAL = 9;
    private static final int HAN_CANNON = 3;
    private static final int CHO_CANNON = 8;
    private static final Row INVALID = new Row(-1);

    public static Row from(int value) {
        return new Row(value);
    }

    public static Row inValid() {
        return INVALID;
    }

    public int value() {
        return value;
    }

    public static Row soldier(Country country) {
        if (country == Country.CHO) {
            return from(CHO_SOLDIER);
        }
        return from(HAN_SOLDIER);
    }

    public static Row cannon(Country country) {
        if (country == Country.CHO) {
            return from(CHO_CANNON);
        }
        return from(HAN_CANNON);
    }

    public static Row general(Country country) {
        if (country == Country.CHO) {
            return from(CHO_GENERAL);
        }
        return from(HAN_GENERAL);
    }

    public static Row edgePiece(Country country) {
        if (country == Country.CHO) {
            return from(CHO_EDGE);
        }
        return from(HAN_EDGE);
    }

    public int diff(Row to) {
        return to.value - this.value;
    }
}
