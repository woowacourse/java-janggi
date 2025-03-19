package janggi.view;

import janggi.Team;
import java.util.List;

public enum PieceType {

    KING(List.of("漢", "楚")),
    GUARD("士"),
    HORSE("馬"),
    ELEPHANT("象"),
    CHARIOT("車"),
    CANNON("包"),
    JOL("卒"),
    BYEONG("兵");

    private static final String BLUE_CODE = "\u001B[34m";
    private static final String RED_CODE = "\u001B[31m";
    private static final String EXIT_CODE = "\u001B[0m";

    private final List<String> values;

    PieceType(List<String> values) {
        this.values = values;
    }

    PieceType(String value) {
        this.values = List.of(value);
    }

    public String getValue(Team team) {
        if (team == Team.HAN) {
            return RED_CODE + values.getFirst() + EXIT_CODE;
        }
        return BLUE_CODE + values.getLast() + EXIT_CODE;
    }
}
