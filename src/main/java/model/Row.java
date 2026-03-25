package model;

import java.util.Objects;

public class Row {
    private static final int HAN_EDGE = 1;
    private static final int CHO_EDGE = 10;
    private static final int HAN_SOLDIER = 4;
    private static final int CHO_SOLDIER = 7;
    private static final int HAN_GENERAL = 2;
    private static final int CHO_GENERAL = 9;
    private static final int HAN_CANNON = 3;
    private static final int CHO_CANNON = 8;
    private final int value;

    private Row(int value) {
        validate(value);
        this.value = value;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Row row = (Row) o;
        return value == row.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    private void validate(int value) {
        if (value < HAN_EDGE || value > CHO_EDGE) {
            throw new IllegalArgumentException("범위에 맞지 않는 숫자입니다.");
        }
    }
}
