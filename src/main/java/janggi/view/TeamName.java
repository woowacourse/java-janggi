package janggi.view;

import janggi.domain.Team;
import java.util.Arrays;

public enum TeamName {
    RED(Team.RED, "한", "\u001B[31m"),
    GREEN(Team.GREEN, "초", "\u001B[32m"),
    ;

    private static final String COLOR_RESET = "\u001B[0m";

    private final Team team;
    private final String country;
    private final String color;

    TeamName(final Team team, final String country, final String color) {
        this.team = team;
        this.country = country;
        this.color = color;
    }

    public static String getCountryName(Team team) {
        return Arrays.stream(TeamName.values())
                .filter(teamName -> teamName.team == team)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("팀 이름 탐색 실패"))
                .country;
    }

    public String getColorName(final String name) {
        return this.color + name + TeamName.COLOR_RESET;
    }
}
