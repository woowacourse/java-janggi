package view;

import domain.piece.Team;

import java.util.Arrays;

public enum TeamColor {

    RED(Team.HAN, "\u001B[31m"),
    GREEN(Team.CHO, "\u001B[32m"),
    WHITE(Team.NONE, "\u001B[37m"),
    ;

    private final Team team;
    private final String color;

    TeamColor(Team team, String color) {
        this.team = team;
        this.color = color;
    }

    public static TeamColor valueOf(Team team) {
        return Arrays.stream(TeamColor.values())
                .filter(color -> color.team == team)
                .findFirst()
                .orElse(WHITE);
    }

    public String getColor() {
        return color;
    }

}
