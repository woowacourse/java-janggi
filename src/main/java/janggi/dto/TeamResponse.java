package janggi.dto;

import janggi.domain.common.Team;

public class TeamResponse {

    private static final String HAN_COLOR = "\u001B[31m";
    private static final String CHO_COLOR = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    private final String name;

    private TeamResponse(String name) {
        this.name = name;
    }

    public static TeamResponse from(Team team) {
        if (team == Team.HAN) {
            return new TeamResponse(HAN_COLOR + team.getName() + RESET);
        }
        return new TeamResponse(CHO_COLOR + team.getName() + RESET);
    }

    public String getName() {
        return name;
    }
}
