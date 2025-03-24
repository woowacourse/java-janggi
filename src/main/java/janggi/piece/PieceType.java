package janggi.piece;

import janggi.Team.Team;
import java.util.List;

public enum PieceType {

    GUNG(List.of("漢", "楚")),
    SA("士"),
    MA("馬"),
    SANG("象"),
    CHA("車"),
    PO("包"),
    JOL("卒"),
    BYEONG("兵");

    private final List<String> titles;

    PieceType(List<String> titles) {
        this.titles = titles;
    }

    PieceType(String value) {
        this.titles = List.of(value);
    }

    public String getTitle(Team team) {
        if (team == Team.HAN) {
            return titles.getFirst();
        }
        return titles.getLast();
    }
}
